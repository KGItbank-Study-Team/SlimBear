package com.kgitbank.slimbear.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberBoardVO {
	private int boardNumber;
	private String boardGroup;
	private String boardTitle;
	private String boardWriter;
	private String boardDate;
	private int boardHits;
	private String boardSubstance;
}
