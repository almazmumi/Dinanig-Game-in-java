
public class ScoresSystem implements Comparable<ScoresSystem> {
	private String name;
	private int score;
	
	public ScoresSystem(String name , int score ) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "ScoresSystem [name=" + name + ", score=" + score + "]";
	}


	@Override
	public int compareTo(ScoresSystem e) {
		// TODO Auto-generated method stub
		return getName().compareToIgnoreCase(e.getName());
	}
	
	
}
