We have a big log file - several gigabytes. 
Each line contains request/response log - with columns like REQUEST_ID, TIMESTAMP, START/END FLAG. 
We need to parse file, and print requests ids that exceeded given time threshold. 
Some of requests contains start log, but has never completed and do not have log with end time.
i.e.
1 1 START
2 2 START
1 4 END
3 8 START
3 15 END
And given timeout threshold as 5.


// Code:
  // 1. parse file into req fomrat
  // 2. pass that file into the function for processing:
List<Pair> parseFile(String filePath) {
	List<Pair> logs = new ArrayList<>();
	BufferedReader br = new BufferedReader(new FileReader(filePath));
	String line;
	
	while((line = br.readLine()) != null) {
		String[] parts = line.split(“ ”);
		Int id = Integer.parseInt(parts[0]);
		Int ts = Integer.parseInt(parsts[1]);
		String flag = parts[2];
		logs.add(new Pair(id, ts, flag));
  }
  return logs;
} 


Class Log{
	Int id;
	Int ts;
	String flag; // 
	Public Log(int id, int ts, String flag) {
		This.id = id;
		This.ts = ts;
		This.flag = flag;
  }
}

Class Pairp {
	Int s;
  Int e;
  Public Pairp (int s, int e) {
  This.s = s;
  This.e = e; 
  }
}


List<Integer> solve(List<Pair> logs, int threshold) {
  Int n = logs.length();
  List<Integer> res = new ArrayList<>(); // to store the correct logs
  map<key, Pairp<start, end>> mp = new HashMap<>();
  for(Pairp p : logs) {
  	Int id = p.id;
  	Int ts = p.ts;
  	String flag = p.flag;
  	if(flag.equals(“START”)) {
  		mp.put(id, new Pairp(ts, INT_MAX));
  	} else if(flag.equals(“END”)) {
  		Pairp p = mp.get(id);
  		p.end = ts;
  		mp.set(id, p);
  		if(p.e - p.s <= threshold)
  			res.add(id);
    }
  }
  Return res;
}



