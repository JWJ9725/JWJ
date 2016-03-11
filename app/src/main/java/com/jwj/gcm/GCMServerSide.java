package com.jwj.gcm;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

public class GCMServerSide {

	/**
	 * 서버 : Sender 객체 선언
	 */
	Sender sender;

	/**
	 * collapseKey 설정을 위한 Random 객체
	 */
	private Random random;

	/**
	 * 구글 서버에 메시지 보관하는 기간(초단위로 4주까지 가능)
	 */
	private int TTLTime = 60;

	/**
	 * 단말기에 메시지 전송 재시도 횟수
	 */
	private int RETRY = 3;

	/*
	 * 보낼메시지
	 */
	String data = "JWJ Server Message";

	/*
	 * 등록된 ID 저장
	 */
	ArrayList<String> idList = new ArrayList<String>();

	public GCMServerSide(String data) {
		// 서버 : GOOGLE_API_KEY를 이용해 Sender 초기화
		this.data = data;
		sender = new Sender(GCMInfo.GOOGLE_API_KEY);
	}

	public void send() {
		new Thread(new Runnable() {

			public void run() {

				try {

					System.out.println("등록된 ID : " + GCMInfo.TEST_REG_ID);

					idList.add(GCMInfo.TEST_REG_ID);
					sendText(data);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

			public void sendText(String msg) throws Exception {

				if (random == null) {
					random = new Random(System.currentTimeMillis());
				}

				String messageCollapseKey = String.valueOf(Math.abs(random
						.nextInt()));

				try {
					// 푸시 메시지 전송을 위한 메시지 객체 생성 및 환경 설정
					Message.Builder gcmMessageBuilder = new Message.Builder();
					gcmMessageBuilder.collapseKey(messageCollapseKey)
							.delayWhileIdle(true).timeToLive(TTLTime);
					gcmMessageBuilder.addData("type", "text");
					gcmMessageBuilder.addData("command", "show");
					gcmMessageBuilder.addData("data",
							URLEncoder.encode(data, "UTF-8"));

					Message gcmMessage = gcmMessageBuilder.build();

					// 여러 단말에 메시지 전송 후 결과 확인
					MulticastResult resultMessage = sender.send(gcmMessage,
							idList, RETRY);

					String output = "GCM 전송 메시지 결과 => "
							+ resultMessage.getMulticastId() + ","
							+ resultMessage.getRetryMulticastIds() + ","
							+ resultMessage.getSuccess();

					System.out.println(output);

				} catch (Exception ex) {
					ex.printStackTrace();
					String output = "GCM 메시지 전송 과정에서 에러 발생 : " + ex.toString();
					System.out.println(output);

				}

			}
		}).start();

	}

	public static void main(String[] args) throws Exception {

		GCMServerSide s = new GCMServerSide("Project num: 894816416630: seq : 1");

		s.send();

	}

}
