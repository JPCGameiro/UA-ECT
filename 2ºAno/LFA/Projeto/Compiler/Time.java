class Time {
	private int ss;
	private int mm;
	static long startTime = 0;
	static long endTime = 0;

	Time(int mm, int ss){
		this.mm = mm;
		this.ss = ss;
	}

	public static void timerstart() {
		startTime = System.currentTimeMillis();
	}
	
	public static void timerstop() {
		endTime = System.currentTimeMillis();
	}
	
	public static Time durationAtual() {
		long atual = System.currentTimeMillis();
		int aux = (int) (atual -startTime);
		Time temp = convertmilitonormal(aux);
		return temp;
	}

	public static Time duration() {
		int aux = (int) (endTime - startTime);
		Time temp = convertmilitonormal(aux);
		return temp;
	}
	
	private static Time convertmilitonormal(int mili) {
		int minutes = (mili/1000) /60;
		int seconds = (mili/1000) % 60;
		return new Time(minutes, seconds);
	}
	
	@Override
	public String toString() {
		String s = String.format("%02d:%02d",mm,ss);
		return s;
	}

	public int getMm() {
		return mm;
	}

	public int getSs() {
		return ss;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mm;
		result = prime * result + ss;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (mm != other.mm)
			return false;
		if (ss != other.ss)
			return false;
		return true;
	}
}
