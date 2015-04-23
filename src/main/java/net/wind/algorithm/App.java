package net.wind.algorithm;

import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		Stack<String> stack = new Stack<String>();
		// a + b * c + (d * e + f ) * g
		Scanner in = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		while (in.hasNextLine()) {
			String s = in.nextLine();
			if ("#".equals(s)) {
				break;
			}
			if (s.matches("^[a-zA-Z]+$")) {
				System.out.print(s + " ");
				sb.append(s + " ");
			} else if (s.matches("[\\+-]")) {
				while (!stack.isEmpty() && stack.peek().matches("[\\+-/\\*]")
						&& !("(").equals(stack.peek())) {
					String old = stack.pop();
					System.out.print(old + " ");
					sb.append(old + " ");
				}
				stack.push(s);
			} else if (s.matches("[\\*/]")) {
				while (!stack.isEmpty() && stack.peek().matches("[\\*/]")
						&& !("(").equals(stack.peek())) {// 栈顶优先级不小于当前元素
					String old = stack.pop();
					System.out.print(old + " ");
					sb.append(old + " ");
				}
				stack.push(s);
			} else if ("(".equals(s)) {// 左括号直接入栈
				stack.push(s);
			} else if (")".equals(s)) {
				while (!stack.peek().equals("(")) {
					String old = stack.pop();
					System.out.print(old + " ");
					sb.append(old + " ");
				}
				stack.pop();// 左括号出栈
			}
			System.out.println(stack.toString());
		}
		while (!stack.isEmpty()) {
			String last = stack.pop();
			System.out.print(last + " ");
			sb.append(last + " ");
		}
		System.out.println("结果是：" + sb.toString());
	}
}
