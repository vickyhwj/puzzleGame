
package eightnum;
import java.util.ArrayList;
import java.util.Scanner;

class State
{
	int num,zero,bu;
	State fa,next;
	public State(int num,int zero,int bu)
	{
		this.num=num;
		this.zero=zero;
		this.bu=bu;
	}
	public State(){}
}

public class Main {
	 int[] vis;
	 State front,rear;
	 int goal,start;
	 ArrayList<State> list;
	 String go="";
	 
	public String getGo() {
		return go;
	}
	public void setGo(String go) {
		this.go = go;
	}
	public  int cantor(int[] a)
	{
		int t=0,sum=0;
		int ji=1*2*3*4*5*6*7*8*9;
		for(int i=1;i<=9;++i)
		{
			t=a[i];
			for(int j=1;j<i;++j)
				if(a[j]<a[i]) --t;
			ji/=(10-i);
			sum+=t*ji;
		}
		return sum;
	}
	public  void nicantor(int[] a,int in)
	{
		int ji=1*2*3*4*5*6*7*8;
		int[] vis=new int[10];
		for(int i=1;i<=9;++i)
		{
			int t=in/ji;
			in=in%ji;
			vis=new int[10];
			for(int j=1;j<i;++j)
			{
				if(a[j]<=t&&vis[j]==0)
				{
					vis[j]=1;t++;j=0;
				}
			}
			a[i]=t;
			if(i==9) break;
			ji/=9-i;
		}
	}

	public  void change(int[] a,int[][] b)
	{
		for(int n=1;n<=9;++n)
		{
			int	x=(n-1)/3+1,y=(n-1)%3+1;
			b[x][y]=a[n];
		}
	}
	public  void change1(int[] a,int[][] b)
	{
		int n=0;
		for(int i=1;i<=3;++i)
			for(int j=1;j<=3;++j)
				a[++n]=b[i][j];
		
		
		
	}
	public  int move(int i)
	{
		
		
		int[][] move={{0,0},{-1,0},{1,0},{0,-1},{0,1}};
		if(front.next.num==goal) return 2;
		int num=front.next.num,zero=front.next.zero,bu=front.next.bu;
		int[] a=new int[10];
		int[][] b=new int[4][4];
		nicantor(a, num);
		change(a, b);
		int x=(zero-1)/3+1,y=(zero-1)%3+1;
		int x1=x+move[i][0],y1=y+move[i][1];
		if(x1<1||x1>3||y1<1||y1>3) return 0;
		//change
		int temp=b[x][y];
		b[x][y]=b[x1][y1];b[x1][y1]=temp;
		change1(a, b);
		int num1=cantor(a);
		if(vis[num1]==1) return 0;
		State p=new State(num1, (x1-1)*3+y1,bu+1);
		rear.next.next=p;
		p.next=rear;
		p.fa=front.next;
		rear.next=p;
		if(num1==goal) return 2;
		vis[num1]=1;
		return 1;
	}
	public  void out()
	{
		State p=rear.next;
		list=new ArrayList<State>();
		while(p!=null)
		{
			list.add(p);
			p=p.fa;
		}
		for(int i=list.size()-1;i>=0;--i)
		{
			int[] a=new int[10];
			nicantor(a, list.get(i).num);
			int[][] b=new int[4][4];
			change(a, b);
			System.out.println(list.get(i).bu);
			for(int j=1;j<=3;++j)
			{
				for(int k=1;k<=3;++k)
					System.out.print(b[j][k]);
				System.out.println();
			}
			System.out.println();
		}
	}
	public  void main1(String start1,String end1) {
		
		vis=new int[9999999];
		front=new State();rear=new State();
		front.next=rear;rear.next=front;
		int[] aa=new int[10];
		int zero=0;
		
		for(int i=1;i<=9;++i)
		{
			aa[i]=Integer.parseInt(start1.substring(i-1, i));
			if(aa[i]==0) zero=i;
		}
		
		start=cantor(aa);vis[start]=1;
		State p=new State(start,zero,0);
		rear.next.next=p;p.next=rear;rear.next=p;
		for(int i=1;i<=9;++i)
			aa[i]=Integer.parseInt(end1.substring(i-1, i));
		goal=cantor(aa);
		
		while(front.next!=rear)
		{
			for(int i=1;i<=4;++i)
				if(move(i)==2)
				{
					out2();return;
				}
			front.next=front.next.next;
		}
		
	}
	public void out2()
	{
		list=new ArrayList<State>();
		State p=rear.next;
		while(p!=null)
		{
			list.add(p);
			p=p.fa;
		}
		for(int i=list.size()-1;i>=0;--i)
		{
			int[] a=new int[10];
			nicantor(a,list.get(i).num);
			for(int j=1;j<=9;++j)
				go+=String.valueOf(a[j]);
			go=go+" ";
		}
		System.out.print(go);
		
	}
	public static void main(String[] args)
	{
		Main m=new Main();
		m.main1("036174825","123456780");
	}

}
