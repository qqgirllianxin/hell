package ps.hell.util.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * Created by amosli on 14-8-12.
 */
public class HelloServerDemo extends Thread {
	public static final int SERVER_PORT = 8090;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread server = new HelloServerDemo();
		System.out.println("启动服务");
		server.start();
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		 Thread client = new HelloClientDemo("测试程序");
		 System.out.println("启动客户");
		 client.start();
	}

	public void run() {
		startServer();
	}

	public void startServer() {
		try {
			System.out.println("HelloWorld TSimpleServer start ....");

			// TProcessor tprocessor = new
			// HelloWorldService.Processor<HelloWorldService.Iface>(new
			// HelloWorldImpl());
			HelloWorldService.Processor<HelloWorldService.Iface> tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(
					new HelloWorldImpl());

			// 简单的单线程服务模型，一般用于测试
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			TServer.Args tArgs = new TServer.Args(serverTransport);
			tArgs.processor(tprocessor);
			// tArgs.protocolFactory(new TBinaryProtocol.Factory());
			tArgs.protocolFactory(new TCompactProtocol.Factory());
			// tArgs.protocolFactory(new TJSONProtocol.Factory());
			TServer server = new TSimpleServer(tArgs);
			server.serve();

		} catch (Exception e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
	}

}