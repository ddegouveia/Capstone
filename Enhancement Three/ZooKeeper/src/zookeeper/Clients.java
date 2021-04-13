package zookeeper;

public class Clients {
	
	import org.apache.zookeeper.KeeperException.Code;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	public class Clients implements Watcher, Closeable {
	   private static final Logger LOG = LoggerFactory.getLogger(Master.class);
	   
	   ZooKeeper zk;
	   String hostPort;
	   volatile boolean connected = false;
	   volatile boolean expired = false;
	   
	   Clients(String hostPort) { 
	       this.hostPort = hostPort;
	   }
	   
	   void startZK() throws IOException {
	       zk = new ZooKeeper(hostPort, 15000, this);
	   }
	   
	   public void process(WatchedEvent e) { 
	       System.out.println(e);
	       if(e.getType() == Event.EventType.None){
	           switch (e.getState()) {
	           case SyncConnected:
	               connected = true;
	               break;
	           case Disconnected:
	               connected = false;
	               break;
	           case Expired:
	               expired = true;
	               connected = false;
	               System.out.println("Exiting due to session expiration");
	           default:
	               break;
	           }
	       }
	   }
	   
	   /**
	    * Check if this client is connected.
	    * 
	    * @return
	    */
	   boolean isConnected(){
	       return connected;
	   }
	   
	   /**
	    * Check if the ZooKeeper session is expired.
	    * 
	    * @return
	    */
	   boolean isExpired(){
	       return expired;
	   }
	   
	   /*
	    * Executes a sample task and watches for the result
	    */
	   
	   void submitTask(String task, TaskObject taskCtx){
	       taskCtx.setTask(task);
	       zk.create("/tasks/task-", 
	               task.getBytes(),
	               Ids.OPEN_ACL_UNSAFE,
	               CreateMode.PERSISTENT_SEQUENTIAL,
	               createTaskCallback,   
	               taskCtx);
	   }
	   
	   StringCallback createTaskCallback = new StringCallback() {
	       public void processResult(int rc, String path, Object ctx, String name) {
	           switch (Code.get(rc)) { 
	           case CONNECTIONLOSS:
	               /*
	                * Handling connection loss for a sequential node is a bit
	                * delicate. Executing the ZooKeeper create command again
	                * might lead to duplicate tasks.
	                */
	               submitTask(((TaskObject) ctx).getTask(), (TaskObject) ctx);
	               
	               break;
	           case OK:
	               LOG.info("My created task name: " + name);
	               ((TaskObject) ctx).setTaskName(name);
	               watchStatus(name.replace("/tasks/", "/status/"), ctx);
	               
	               break;
	           default:
	               LOG.error("Something went wrong" + KeeperException.create(Code.get(rc), path));
	           }
	       }
	   };
	   

	   protected ConcurrentHashMap<String, Object> ctxMap = new ConcurrentHashMap<String, Object>();
	   
	   void watchStatus(String path, Object ctx){
	       ctxMap.put(path, ctx);
	       zk.exists(path, 
	               statusWatcher, 
	               existsCallback, 
	               ctx);
	   }
	   

}
