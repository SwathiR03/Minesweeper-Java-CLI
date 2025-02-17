import java.util.*;
public class Minesweeper2 {
    
    // Function to print the user grid on the screen and if InitBoardFlag is true then
    // initialise the grid before printing.
    public static void PrintMyBoard(String [][]uboard, int r, int c, boolean InitBoardFlag)
    {
      
      for (int i = 1; i <= r; i++) 
      {
            for (int j = 1; j <= c; j++) 
            {
                if (InitBoardFlag)        //Initialising the grid if Flag is true
                     uboard[i][j] = "-";
                System.out.print(uboard[i][j]+"  ");
                //uboard[i][j] = "-";
            }
            System.out.println();
      }
    }
    // Function to check if user has completed the board: Returns the count of cells without mines.
    public static int ChkBoardComplete(String [][]uboard,String [][]ms1, int r, int c)
    {
      int EmptyCellCount=0;
      int MineCount =0;
      for (int i = 1; i <= r; i++) 
      {
            for (int j = 1; j <= c; j++) 
            {
                if (uboard[i][j] =="-")        
                     EmptyCellCount++;
                if(ms1[i][j]=="*")
                    MineCount++;
                    
            }
      }
      if(EmptyCellCount == MineCount)
      EmptyCellCount =  0;
      
      return EmptyCellCount;
      
    }
    
    //Function to pause the screen for a while
    public static void WaitScreen ()
    {
        int n = 0;
        while (true)
        {
            n++;
            if (n >= 1000) break;
        }
    }
    public static void main(String[] args) 
    { 
        
        Scanner sc = new Scanner(System.in);
        
        // Accept from the user the grid size for the game and the probability of having mine
        System.out.println("Enter the number of rows");
        int m = sc.nextInt();
        System.out.println("Enter the number of columns");
        int  n = sc.nextInt();
        System.out.println("Enter the probability of each cell having a mine(The number must be a decimal between 0.0 and 1.0)");
        double p =sc.nextDouble();
        
        // Variables declaration
        int ucol = 0;
        int urow = 0;
        String[][] mines = new String[m+2][n+2];       //mines array          
        int[][] sol = new int[m+2][n+2];                 //array to store the number of mines present
        String [][] userboard = new String[m+2][n+2];    //array to store user input
        String[][] ms = new String[m+2][n+2];            //array to print the board with the user input
        
        int i, j;
        int f =0;
        int EmptyCells =0;
        
        // game grid is [1..m][1..n], border is used to handle boundary cases
        // Populating the mines array 
        for ( i = 1; i <= m; i++)
        {
            for ( j = 1; j <= n; j++)
            {
                if (Math.random()< p)
                    mines[i][j] = "*";
                else
                    mines[i][j] = "-";
                
            }
        }
     
       
        // sol[i][j] = # mines adjacent to cell (i, j)
        // Populating an array with No. of mines in the cells adjacent to mines
        for ( i = 1; i <= m; i++)
        {    
            for ( j = 1; j <= n; j++)
            {    // (ii, jj) indexes to check the neighbouring cells
                for (int ii = i - 1; ii <= i + 1; ii++)//Moving across rows
                { 
                    for (int jj = j - 1; jj <= j + 1; jj++)//Moving across columns
                    { 
                        if (mines[ii][jj] == "*") 
                              sol[i][j]++;
                    }
                }
            }
        }
        // Populating all cells with mines and No. of  mines . This is the solution array
        for ( i = 1; i <= m; i++) 
        {
            for ( j = 1; j <= n; j++) 
            {
                if (mines[i][j] == "*")                 
                   ms[i][j] = "*";
                else  
                   ms[i][j] =Integer.toString(sol[i][j]);
            }
        }
        
      System.out.println("Printing your Grid");
      PrintMyBoard(userboard, m, n, true);//Initialise the grid and display it
      WaitScreen();

      //  Accept cell number from user =>  user is clicking which cell
      while(true)
      {
            //System.out.print("\f");;
            System.out.println("Enter the cell number(Eg: 1 1)  OR 0 0 to QUIT the game");
            ucol = sc.nextInt();
            urow = sc.nextInt();
            // First check for exit condition
            if (ucol ==0 && urow == 0)
            {
                System.out.println("Exiting the game, Thanks for playing");
                System.exit(0);
            }
            if(ucol>m ||ucol<1||urow<1|| urow>n)
            {
                System.out.println("ERROR:CELL NUMBER OUT OF RANGE.");  
                continue;
            }
            userboard[ucol][urow] = ms[ucol][urow];   // Populating user cell from solution array
            
            if(userboard[ucol][urow]=="*")
            {
                
                 System.out.println("YOU HAVE HIT A MINE. SORRY. BETTER LUCK NEXT TIME");
                 PrintMyBoard(mines,m,n,false);
                 WaitScreen();
                 System.exit(0);
            } 
            else 
            {
                 PrintMyBoard(userboard,m,n,false);
                 EmptyCells = 0;
                 EmptyCells = ChkBoardComplete (userboard,ms,m,n);
                 
                 if (EmptyCells == 0)        // If all cells are filled 
                 {
                     System.out.println("CONGRATS !!!! YOU HAVE WON");
                     WaitScreen();
                     System.exit(0);
                 }
            }
      }
    }
}

