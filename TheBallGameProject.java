import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class TheBallGameProject extends Application //i = column j = row
{
   private GamePane [][] grid = new GamePane [4][4];
   
   public void start (Stage stage)
   {
      GridPane gp = new GridPane();

      //4x4 grid of gamepanes
      for (int i=0;i<4;i++)
      {
         gp.setVgap(5);
         gp.setHgap(5);
         for(int j=0;j<4;j++)
         {
            //gamepanes
            grid[i][j]=new GamePane(i,j);
            gp.add(grid[i][j],i,j);
            grid[i][j].pc.drawCircle();
            grid[i][j].setBallVis(true);
         }
      }
      
      //setting 0,2 to inactive
      grid[0][2].pc.clearCircle();
      grid[0][0].setB1Vis(true);
      grid[2][2].setB2Vis(true);
      grid[0][0].draw();
      
      //start of the method of whether moves can be made
      
      //
      FlowPane top = new FlowPane();
      top.setPrefSize(50,10);
      FlowPane left = new FlowPane();
      left.setPrefSize(50,10);
      FlowPane right = new FlowPane();
      right.setPrefSize(50,10);
      FlowPane bot = new FlowPane();
      bot.setPrefSize(50,10);
      top.getChildren().add(l1);
      //
      bp.setLeft(left);
      bp.setRight(right);
      bp.setBottom(bot);
      bp.setCenter(gp);
      bp.setTop(top);
      //
      Scene scene = new Scene (bp,600,600);
      stage.setScene(scene);
      stage.setTitle("Ball Game");
      stage.show();
      
   } //end start
   
   /******************/
   /***CLICK METHOD***/
   public void click(int x, int y, String dir ) //3 parameters
   {
      System.out.println("hi");
      //if button is clicked, set that ball and ball next to it invisible and ball it is jumping to, to visible
      try
      {
         if (dir.equals("top"))
         {
            grid[x][y].setBallVis(false);
            grid[x][y+1].setBallVis(false);
            grid[x][y+2].setBallVis(true);
         }
      }
      catch (ArrayIndexOutOfBoundsException ai)
      {
         
      }
      try
      {
         if (dir.equals("left"))
         {
            grid[x][y].setBallVis(false);
            grid[x-1][y].setBallVis(false);
            grid[x-2][y].setBallVis(true);
         }
      }
      catch (ArrayIndexOutOfBoundsException ai)
      {
         
      }
      try
      {
         if (dir.equals("bottom"))
         {
            grid[x][y].setBallVis(false);
            grid[x][y-1].setBallVis(false);
            grid[x][y-2].setBallVis(true);
         }
      }
      catch (ArrayIndexOutOfBoundsException ai)
      {
         
      }
      try
      {
         if (dir.equals("right"))
         {
            grid[x][y].setBallVis(false);
            grid[x+1][y].setBallVis(false);
            grid[x+2][y].setBallVis(true);
         }
      }
      catch (ArrayIndexOutOfBoundsException ai)
      {
         
      }
   }  

   BorderPane bp = new BorderPane();
   Label l1 = new Label("Welcome to the game");
   
   public class GamePane extends GridPane
   {
      PaintCanvas pc = new PaintCanvas();
      Button b1 = new Button("1");
      Button b2 = new Button("2");
      Button b3 = new Button("3");
      Button b4 = new Button("4");
      //boolean for whether the ball is visible
      private boolean ball = true;
      boolean active = true;
      //boolean for button (dont know if i need one for each)
      private boolean b1Vis = false;
      private boolean b2Vis = false;
      private boolean b3Vis = false;
      private boolean b4Vis = false;
      
      int xPos;
      int yPos;
      
      public GamePane(int i, int j)
      {
        xPos=i;
        yPos=j;
        //creating 4 buttons for top,left,right,bottom of each grid
        //creating circles for the center of eacg grid, circle needs to be in here
        //b1, b2, b3, b4 go in a counter clockwise motion around circle
        add(pc,1,1);
        
        b1.setPrefSize(80,20);
        add(b1,1,0);
        
        b2.setPrefSize(20,80);
        add(b2,2,1);
        
        b3.setPrefSize(80,20);
        add(b3,1,2);
        
        b4.setPrefSize(20,80);
        add(b4,0,1);
        
        b1.setOnAction(new ButtonListener());
        b2.setOnAction(new ButtonListener());
        b3.setOnAction(new ButtonListener());
        b4.setOnAction(new ButtonListener());
      }
      
      public void move()
      {
         if (grid[xPos][yPos].getBallVis()==true)
         {
            //check left
            if ((xPos>1 && yPos>=0 && yPos<=3) || (xPos<4 && yPos<1 && yPos>2))
            {
               if ((grid[xPos-1][yPos].getBallVis()==true && grid[xPos-2][yPos].getBallVis()==false))
               {
                  grid[xPos][yPos].pc.drawCircle();
                  grid[xPos][yPos].getB2Vis(); //then this is a valid move for i,j and need to add b2
               }
            }
            //check right
            if ((xPos<2 && yPos<=3 && yPos>=0) || (xPos<4 && yPos<1 && yPos>2))
            {
               if ((grid[xPos+1][yPos].getBallVis()==true  && grid[xPos+2][yPos].getBallVis()==false))
               {
                  grid[xPos][yPos].pc.drawCircle();
                  grid[xPos][yPos].getB4Vis(); //valid move and need to add b4
               }
            }
            //check up
            if ((xPos>=0 && yPos>1 && yPos<=4) || (xPos>2 && yPos<1 && yPos>2))
            {
               if ((grid[xPos][yPos-1].getBallVis()==true  && grid[xPos][yPos-2].getBallVis()==false))
               {
                  grid[xPos][yPos].pc.drawCircle();
                  grid[xPos][yPos].getB3Vis(); //valid move and need to add b3
               }
            }
            //check down
            if ((xPos<4 && yPos>=0 && yPos<2) || (xPos<3 && yPos<1 && yPos>2))
            {
               if ((grid[xPos][yPos+1].getBallVis()==true  && grid[xPos][yPos+2].getBallVis()==false))
               {
                  grid[xPos][yPos].pc.drawCircle();
                  grid[xPos][yPos].getB1Vis(); //valid move and need to add b1
               }
            }
         }
      }
      
      public void draw() //called everytime a change is made
      {
         //drawing the game, draw circle if it is active or clear if it is not need to fix find a different if/else statement
         for (int i=0;i<4;i++)
         {
            for(int j=0;j<4;j++)
            {
               grid[i][j].b1.setVisible(grid[i][j].getB1Vis());
               
               grid[i][j].b2.setVisible(grid[i][j].getB2Vis());
               
               grid[i][j].b3.setVisible(grid[i][j].getB3Vis());
               
               grid[i][j].b4.setVisible(grid[i][j].getB4Vis());
            }
         } 
      }
      
      //setters and getters for ball and buttons
      public boolean getBallVis()
      {
         return ball;
      }
      
      public void setBallVis(boolean in)
      {
         ball = in;
      }
      
      public boolean getB1Vis()
      {
         return b1Vis;
      }
      
      public void setB1Vis(boolean b1) //show which button
      {
         b1Vis = b1;
      }
      
      public boolean getB2Vis()
      {
         return b2Vis;
      }
      
      public void setB2Vis(boolean b2) //show which button
      {
         b2Vis = b2;
      }
      
      public boolean getB3Vis()
      {
         return b3Vis;
      }
      
      public void setB3Vis(boolean b3) //show which button
      {
         b3Vis = b3;
      }
      
      public boolean getB4Vis()
      {
         return b4Vis;
      }
      
      public void setB4Vis(boolean b4) //show which button
      {
         b4Vis = b4;
      }
      
      /********************************************/
      /************BUTTON LISTENER*****************/
      public class ButtonListener implements EventHandler<ActionEvent>
      {
         public void handle (ActionEvent e)
         {
            //if statements for each button
            if (e.getSource() == b1)
            {
               click(xPos,yPos,"top");
               move();
               //draw();
               System.out.println("button 1 was pressed at " +xPos +" and "+yPos);
               System.out.println("ball at " +xPos +" and " +yPos +" will be removed");
               System.out.println("ball at " +xPos +" and " +(yPos+1) +" will be removed");
               System.out.println("ball at " +xPos +" and " +(yPos+2) +" will be visible");
            }
            if (e.getSource() == b2)
            {
               click(xPos,yPos,"left");
               move();
               //draw();
               System.out.println("button 2 was pressed at " +xPos +" and "+yPos);
               System.out.println("ball at " +xPos +" and " +yPos +" will be removed");
               System.out.println("ball at " +(xPos-1) +" and " +yPos +" will be removed");
               System.out.println("ball at " +(xPos-2) +" and " +yPos +" will be visible");
            }
            if (e.getSource() == b3)
            {
               click(xPos,yPos,"bottom");
               move();
               //draw();
               System.out.println("button 3 was pressed at " +xPos +" and "+yPos);
               System.out.println("ball at " +xPos +" and " +yPos +" will be removed");
               System.out.println("ball at " +xPos +" and " +(yPos-1) +" will be removed");
               System.out.println("ball at " +xPos +" and " +(yPos-2) +" will be visible");
            }
            if (e.getSource() == b4)
            {
               click(xPos,yPos,"right");
               move();
               //draw();
               System.out.println("button 4 was pressed at " +xPos +" and "+yPos);
               System.out.println("ball at " +xPos +" and " +yPos +" will be removed");
               System.out.println("ball at " +(xPos+1) +" and " +yPos +" will be removed");
               System.out.println("ball at " +(xPos+2) +" and " +yPos +" will be visible");
            }
            draw();
         }
      }
   } //end gamepane class
   
   public class PaintCanvas extends Canvas
   {
      private GraphicsContext gc = getGraphicsContext2D();
      
      public PaintCanvas()
      {
         setHeight(80);
         setWidth(80);
      }
      
      public void drawCircle()
      {
         gc.setFill(Color.GREEN);
         gc.fillOval(0,0,80,80);
      }
      
      public void clearCircle(/*GraphicsContext gc*/)
      {
         //gc.setFill(Color.GREEN);
         gc.clearRect(0,0,80,80);
      }
   }
   
   public static void main (String[] args)
   {
      launch(args);
   }
}