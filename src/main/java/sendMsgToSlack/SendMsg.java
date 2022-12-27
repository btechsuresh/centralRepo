package sendMsgToSlack;

import java.io.File;
import java.io.IOException;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import io.github.cdimascio.dotenv.Dotenv;

public class SendMsg {
	
	static Dotenv dotenv=Dotenv.load();
	private static String webHookUrl=dotenv.get("webHookUrl");
	private static String oAuthToken=dotenv.get("oAuthToken");
	private static String slackChannel=dotenv.get("slackChannelName");

		
	public static void sendTestExecutionStatusToSlack(String message) throws Exception {
		try {
			
			
		StringBuilder messageBuider = new StringBuilder();
		messageBuider.append(message);
		Payload payload = Payload.builder().channel(slackChannel).text(messageBuider.toString()).build();

		WebhookResponse webhookResponse = Slack.getInstance().send(webHookUrl, payload);
		webhookResponse.getMessage();
		System.exit(0);
		} catch (IOException e) {
		System.out.println("Unexpected Error! WebHook:" + webHookUrl);
		}
		}

		//Upload test execution reports to the Slack channel
		public void sendTestExecutionReportToSlack(String testReportPath) throws Exception {
		String url = "https://slack.com/api/files.upload ";
		try {
		HttpClient httpclient = HttpClientBuilder.create().disableContentCompression().build();
		HttpPost httppost = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		FileBody fileBody = new FileBody(new File(testReportPath));
		builder.setMode(HttpMultipartMode.EXTENDED);
		builder.addPart("file", fileBody);
		builder.addTextBody("channels", slackChannel);
		builder.addTextBody("token", oAuthToken);
		httppost.setEntity(builder.build());
		HttpResponse response = null;
		response = httpclient.execute(httppost);
		HttpEntity result = ((BasicClassicHttpRequest) response).getEntity();
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		
	

}
