package com.mcloud.poker;

import java.util.List;

import lombok.Data;

/**
 * 玩家
 * 
 * @author Sobey
 *
 */
@Data
public class Player {

	/**
	 * 最终牌
	 */
	public List<Card> finalCard;

	/**
	 * 底牌
	 */
	public List<Card> handCard;

	/**
	 * 玩家名
	 */
	public String name;

}
