package state;

public class State<T> {

	private T state;
	private double cost;
	private State<T> cameFrom;

	public State(T state) {
		this.state = state;
		cameFrom = null;
		cost = 1;
	}

	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	
	
//	public boolean equals(State<T> state) {
//		return this.state.equals(state.getState());
//	}

	 @SuppressWarnings("unchecked")
	@Override
	 public boolean equals(Object state) {
	 return this.state.equals(((State<T>) state).getState());
	 }

	@Override
	public int hashCode() {
		return state.hashCode();
	}

	@Override
	public String toString() {
		return getState().toString();
	}

}
