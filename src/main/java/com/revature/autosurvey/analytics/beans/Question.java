package com.revature.autosurvey.analytics.beans;

import java.util.List;

import lombok.Data;

@Data
public class Question {
	private QuestionType questionType;
	private String title;
	private String helpText;
	private Boolean isRequired;
	private List<String> choices;
	private Boolean hasOtherOption;

	public enum QuestionType {
		CHECKBOX, DROPDOWN, RADIO, SHORT_ANSWER, MULTIPLE_CHOICE
	}
}
