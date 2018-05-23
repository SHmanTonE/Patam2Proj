//package test;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.LinkedList;
//
//import board.PipeGameBoard;
//import board.PipeGameBoard.FromDirecetion;
//import board.PipeGameBoardSearchable;
//import solution.Solution;
//import state.State;
//
//public class Tests {
//
//	public static void main(String[] args) {
//		char[][] p = { { 's', '-', '7' }, { '-', ' ', '|' }, { 'L', ' ', 'g' } };
//		char[][] p2 = { { 's', '|', 'L' }, { '-', ' ', '|' }, { 'L', ' ', 'g' } };
//		PipeGameBoard b = new PipeGameBoard(p);
//		PipeGameBoardSearchable bs = new PipeGameBoardSearchable(b);
//		PipeGameBoard b2 = new PipeGameBoard(p2);
//		PipeGameBoardSearchable bs2 = new PipeGameBoardSearchable(b2);
//		System.out.println(p.hashCode());
//		System.out.println(b.hashCode());
//		HashSet<State<PipeGameBoard>> closedSet = new HashSet<State<PipeGameBoard>>();
//		LinkedList<State<PipeGameBoard>> openlist = new LinkedList<State<PipeGameBoard>>();
//		State<PipeGameBoard> possibleState = new State<PipeGameBoard>(b);
//		State<PipeGameBoard> possibleState2 = new State<PipeGameBoard>(b);
//		State<PipeGameBoard> possibleState22 = new State<PipeGameBoard>(b2);
//		State<PipeGameBoard> possibleState222 = new State<PipeGameBoard>(b2);
//		
//		System.out.println(b.howFar(b.getStartRowIndex(),b.getStartColIndex(), FromDirecetion.start));
//		System.out.println(possibleState.hashCode());
//		System.out.println(possibleState2.hashCode());
//		System.out.println(possibleState.equals(possibleState2));
//		
//		System.out.println(possibleState.hashCode());
//		System.out.println(possibleState22.hashCode());
//		System.out.println(possibleState.equals(possibleState22));
//		openlist.add(possibleState);
//		closedSet.add(possibleState);
//		if (closedSet.contains(possibleState))
//			if (!openlist.contains(possibleState2))
//				;
//		// PipeGameBoard s = new PipeGameBoard("1111\n2222\n3333");
//		// System.out.println(s);
//
//		// System.out.println(p[0][0]);
//		// System.out.println(b.getBoard()[0][0]);
//		//
//		// System.out.println(p[0][0]);
//		// System.out.println(b.getBoard()[0][0]);
//		//
//		// String problem = new String("-------L\n" + "L-|--|-L\n" + "L------L\n" +
//		// "L--|-s-g\n");
//
//	}
//
//	public Solution<State<PipeGameBoard>> search(PipeGameBoardSearchable bs) {
//
//		LinkedList<State<PipeGameBoard>> openList = new LinkedList<State<PipeGameBoard>>();
//		openList.add(bs.getInitialState());
//		HashSet<State<PipeGameBoard>> closedSet = new HashSet<State<PipeGameBoard>>();
//		while (!openList.isEmpty()) {
//			State<PipeGameBoard> currentState = openList.pop();
//			closedSet.add(currentState);
//			long time = System.currentTimeMillis();
//			if (bs.isGoalState(currentState)) { // if goal reached
//				time = System.currentTimeMillis() - time;
//				System.out.println("time : " + time);
//				System.out.println("finished");
//			}
//			ArrayList<State<PipeGameBoard>> possibleStates = bs.getPossibleStates(currentState);
//			for (State<PipeGameBoard> possibleState : possibleStates) {
//				if ((!closedSet.contains(possibleState)) && (!openList.contains(possibleState))) {
//					possibleState.setCameFrom(currentState);
//					possibleState.setCost(possibleState.getCost() + currentState.getCost());// ??
//					openList.add(possibleState);
//				}
//			}
//		}
//		return null;
//	}
//}
