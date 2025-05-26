package com.iss.test;

public class PascalsTriangle {
	public static void main(String[] args) {
		int x=1;
		int y=3;
		for(int i=1;i<6;i++)
		{
			
			for(int j=1;j<6;j++)
			{
//				
				if(i==2)
				{
				System.out.print(1+"  ");
				}
				else
				{
					System.out.print(x+"  ");
				}
				if(i==j)
				{
					break;
				}
//				
			}
			y=y-1;
			x=x+1;
			System.out.println();
		}
	}

}
