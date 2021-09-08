package com.revature.autosurvey.analytics.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.util.json.Jackson;
import com.revature.autosurvey.analytics.beans.Survey;

import lombok.Data;

@Data
@Component
public class MessageSender {

	private final QueueMessagingTemplate queueMessagingTemplate;
	private MessageBuilder<String> builder;
	private List<UUID> sentMessages;
	
	@Autowired
	public MessageSender(AmazonSQSAsync sqs) {
		this.queueMessagingTemplate = new QueueMessagingTemplate(sqs);
		this.sentMessages = new ArrayList<>();
	}

	@Scheduled(fixedDelay = 5000)
	public void sendObject() {
		Survey survey = new Survey();
		this.queueMessagingTemplate.send(SQSQueueNames.TEST_QUEUE, MessageBuilder.withPayload(Jackson.toJsonString(survey)).build());
		System.out.println("sending a survey");
	}

	@Async
	public void sendSurveyId(String queueName, String surveyId) {
		Message<String> message = MessageBuilder.withPayload(Jackson.toJsonString(surveyId)).build();
		sentMessages.add(message.getHeaders().getId());
		this.queueMessagingTemplate.send(queueName, message);
	}
	
}
