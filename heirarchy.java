Suppose you have a graph of Google's organizational structure where:
Each node N corresponds to a person
If N reports to M (i.e. M is N's boss), M is the parent of N
If P reports to N (i.e. N is P's boss), P is the child of N

At Google, someone's "employee score" is the total number of reports (including themselves).

Compute employee score for all the nodes
Follow-up: if employee change its boss from A to B, how we going to calculate employee score now?

interviewer expectation: create structure of employee and using that implement the function.




// code :
int[] res; // store the score 

class Emp {
  String name;
  int id; // 0 -> n-1
  Emp manager;
  List<Employee> reports;

  public Emp(String name, int id){
    this.name = name;
    this.id = id;
    this.reports = new ArrayList<>();
  }
}

class EmpScoreCal {
  private int calculateEmpScore(Emp emp) {
    int score = 1;  // including itself

    // recursively calc all the emp under it
    for(Emp e : emp.reports) {
      score += calculateEmpScore(e);
    }

    return score;
  }


  private void changeBoss(Emp emp, Emp newBoss) {
    if(emp!=null) {
      int score_emp = res[emp.id]; // all the reportess under it will also get changed
      
      if(emp.manager!=null && emp.manager.reports!=null) {
        emp.manager.reports.remove(emp);
        res[emp.manager.id] -= score_emp;
      }
      // update the curr emp's manager
      emp.manager = newBoss;

      // update the newBoss reportees list
      if(newBoss!=null) {  
        if(newBoss.reports == null) {
          newBoss.report = new ArrayList<>();
        }
        newBoss.reports.add(emp);
        res[newBoss.id] += score_emp;
      }
      
    }
    
  }


  public int[] calcuateScoreMain(Emp root, int n) {
    if(root == null)
        return;

    Queue<Emp> q = new LinkedList<>();
    q.offer(root);

    while(!q.isEmpty()) {
      Emp curr = q.poll();
      int score = calculateEmpScore(curr);
      res[curr.id] = score;
        
      for(Emp rep : curr.reports){
        q.offer(rep);
      }
    }

    return res;
  }

  public int solution(Emp root, int n) {
    res = calcuateScoreMain(root, n);
    return res;
  }
    
