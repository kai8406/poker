package com.mcloud.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) {

		/**
		 * 1.初始化54张牌,并随机打乱
		 * 
		 * 2.初始化参与人数
		 * 
		 * 3.发放公共牌五张
		 * 
		 * 4.根据人数每人发放2张底牌
		 * 
		 * 5.将每人手上的2张底牌和五张公共牌组合,并选出最大5张牌
		 * 
		 * 6.每人对比,得出最大.
		 */

		// Step.1 初始化54张牌,并随机打乱

		List<Card> cards = initCard();
		// cards.stream().forEach(s -> System.out.println(s));

		// Step.2 初始化参与人数

		List<Player> players = initPlayer(2);

		// Step.3 发放公共牌五张

		System.out.println(cards.size());

		int num = 0;

		List<Card> commonCards = new ArrayList<>();

		Iterator<Card> i = cards.iterator();

		// 避免触发fail-fast ,用迭代器实现,从牌池中移除已发的牌
		while (i.hasNext()) {

			if (num >= 5) {
				break;
			}

			Card c = i.next();
			commonCards.add(c);

			i.remove();

			++num;
		}

		// Step.4 根据人数每人发放2张底牌

		for (Player p : players) {

			List<Card> list = new ArrayList<>();

			int j = 0;

			while (i.hasNext()) {

				if (j >= 2) {
					break;
				}

				Card c = i.next();
				list.add(c);

				i.remove();

				++j;
			}

			p.setHandCard(list);

		}

		// Step.5 将每人手上的2张底牌和五张公共牌组合,并选出最大5张牌

		for (Player p : players) {

			List<Card> finalCards = new ArrayList<>();

			List<Card> handCard = p.getHandCard();

			finalCards.addAll(handCard);
			finalCards.addAll(commonCards);

			Collections.sort(finalCards);

			p.setFinalCard(finalCards);
		}

		// Step 6.每人对比,得出最大.

		Player winner = compareToPlayer(players);
		System.out.println("winner is:" + winner);

	}

	private static Player compareToPlayer(List<Player> players) {

		// 1.确认牌型顺序,这里实现同花大顺和四条

		// 2.player匹配顺序

		// 通过TreeMap进行排序.第一个即使winner
		Map<Integer, Object> map = new TreeMap<>();

		for (Player player : players) {

			Integer rank = Rank(player.getFinalCard());

			map.put(rank, player);
		}

		return (Player) map.get(0);

	}

	private static int Rank(List<Card> cards) {

		// 同花大顺的优先级最高,依次排布.最低牌面就是比最大牌的大小.返回的数越小,说明牌面越大

		// 同花大顺的比较,注意还有花色的比较

		if (royalFlush(cards)) {
			return 0;
		}
		if (fourKind(cards)) {
			return 4;
		}

		return 999;

	}

	/**
	 * 判断是否同花大顺
	 * 
	 * @param cards
	 * @return
	 */
	private static boolean royalFlush(List<Card> cards) {

		Set<Color> set = new HashSet<>();

		int max = 0;
		int min = 14;

		for (Card card : cards) {
			set.add(card.getColor());

			if (card.getNumber() > max) {// 和每一张牌比较,牌面大于max的,重新给max赋值.
				max = card.getNumber();
			}

			if (card.getNumber() < min) {// 和每一张牌比较,牌面小于min的,重新给min赋值.
				min = card.getNumber();
			}
		}

		if (set.size() == 1 && max == 14 && min == 10) {

			// 1.通过Set的唯一性,判断set的大小,set.size=1说明是一个花色
			// 2.因为牌面已经排过序了,所以通过确认最大的牌是A(14),最小的牌是10,就可以确定他是同花大顺.

			return true;
		}

		return false;
	}

	/**
	 * 判断是否四条
	 * 
	 * @param cards
	 * @return
	 */
	private static boolean fourKind(List<Card> cards) {

		// TODO

		return false;
	}

	private static List<Player> initPlayer(int playerNumber) {

		if (playerNumber < 2 && playerNumber > 22) {
			// 抛出异常.
		}

		List<Player> list = new ArrayList<>();

		for (int i = 0; i < playerNumber; i++) {
			Player player = new Player();
			player.setName("Play" + i);
			list.add(player);
		}

		return list;

	}

	private static List<Card> initCard() {

		List<Card> list = new ArrayList<>();

		for (Color color : Color.values()) {

			// A为14
			for (int i = 2; i < 15; i++) {
				Card card = new Card();
				card.setColor(color);
				card.setNumber(i);
				list.add(card);
			}
		}

		Collections.shuffle(list);

		return list;
	}

}
