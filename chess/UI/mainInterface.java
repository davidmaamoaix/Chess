package chess.UI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class mainInterface extends Application{
	int[] focus=new int[] {-1,-1};
	boolean blackTurn=false;
	Group root;
	Group display;
	int[][] pieces=new int[][] {
		{-2,-3,-4,-5,-6,-4,-3,-2},
		{-1,-1,-1,-1,-1,-1,-1,-1},
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{1,1,1,1,1,1,1,1},
		{2,3,4,6,5,4,3,2},
	};
	
	@Override
	public void start(Stage stage){
		stage.setTitle("DavidM's Chess");
		stage.setWidth(720);
		stage.setHeight(740);
		stage.show();
		BorderPane pane=new BorderPane();
		root=new Group();
		pane.setCenter(root);
		Scene scene=new Scene(pane);
		stage.setScene(scene);
		build();
	}
	
	void build(){
		Rectangle bg=new Rectangle(10,10,700,700);
		bg.setFill(Color.rgb(136,93,50));
		root.getChildren().add(bg);
		boolean black=false;
		for(int i=0;i<8;i++){
			black=!black;
			for(int j=0;j<8;j++){
				final int x=i;
				final int y=j;
				Rectangle block=new Rectangle(20+(j*85),20+(i*85),85,85);
				if(black) block.setFill(Color.rgb(89,40,34));
				else block.setFill(Color.rgb(189,162,113));
				black=!black;
				block.setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent e){
						setFocus(x,y);
					}
				});
				root.getChildren().add(block);
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				Image img=null;
				switch(pieces[i][j]){
					case -6:img=new Image("blackQueen.png");break;
					case -5:img=new Image("blackKing.png");break;
					case -4:img=new Image("blackBishop.png");break;
					case -3:img=new Image("blackKnight.png");break;
					case -2:img=new Image("blackRook.png");break;
					case -1:img=new Image("blackPawn.png");break;
					case 1:img=new Image("whitePawn.png");break;
					case 2:img=new Image("whiteRook.png");break;
					case 3:img=new Image("whiteKnight.png");break;
					case 4:img=new Image("whiteBishop.png");break;
					case 5:img=new Image("whiteKing.png");break;
					case 6:img=new Image("whiteQueen.png");break;
				}
				if(pieces[i][j]!=0) {
					final int x=i;
					final int y=j;
					ImageView iv=new ImageView();
					iv.setImage(img);
					iv.setFitWidth(75);
					iv.setFitHeight(75);
					iv.setX(20+(j*85+5));
					iv.setY(20+(i*85+5));
					iv.setOnMouseClicked(new EventHandler<MouseEvent>(){
						@Override
						public void handle(MouseEvent e){
							setFocus(x,y);
						}
					});
					root.getChildren().add(iv);
				}
			}
		}
	}
	
	//All the "x" below means "y", all the "y" below means "x"
	//I screwed up here
	void setFocus(int x,int y){
		
		//Compare Setup
		int[][] compare=new int[8][8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				compare[i][j]=pieces[i][j];
			}
		}
		
		if((blackTurn&&pieces[x][y]<0)||(!blackTurn&&pieces[x][y]>0)){
			build();
			focus[0]=x;
			focus[1]=y;
			ImageView iv=new ImageView();
			Image img=new Image("focus.png");
			iv.setImage(img);
			iv.setFitWidth(85);
			iv.setFitHeight(85);
			iv.setX(20+(y*85));
			iv.setY(20+(x*85));
			root.getChildren().add(iv);
		}else if(focus[0]!=-1){
			
			//Pawn
			if(pieces[focus[0]][focus[1]]==1||pieces[focus[0]][focus[1]]==-1){
				if(focus[0]==1&&pieces[focus[0]][focus[1]]==-1&&pieces[x][y]==0){
					if((x-focus[0]==2&&focus[1]==y)||(x-focus[0]==1&&focus[1]==y)){
						pieces[x][y]=pieces[focus[0]][focus[1]];
						pieces[focus[0]][focus[1]]=0;
					}
				}else if(focus[0]==6&&pieces[focus[0]][focus[1]]==1&&pieces[x][y]==0){
					if((focus[0]-x==2&&focus[1]==y)||(focus[0]-x==1&&focus[1]==y)){
						pieces[x][y]=pieces[focus[0]][focus[1]];
						pieces[focus[0]][focus[1]]=0;
					}
				}else{
					if(pieces[focus[0]][focus[1]]==-1&&pieces[x][y]==0){
						if(x-focus[0]==1&&focus[1]==y){
							pieces[x][y]=pieces[focus[0]][focus[1]];
							pieces[focus[0]][focus[1]]=0;
						}
					}
					if(pieces[focus[0]][focus[1]]==1&&pieces[x][y]==0){
						if(focus[0]-x==1&&focus[1]==y){
							pieces[x][y]=pieces[focus[0]][focus[1]];
							pieces[focus[0]][focus[1]]=0;
						}
					}
				}
				if(abs(y-focus[1])==1&&pieces[x][y]!=0){
					if(x-focus[0]==1&&pieces[focus[0]][focus[1]]==-1){
						pieces[x][y]=pieces[focus[0]][focus[1]];
						pieces[focus[0]][focus[1]]=0;
					}
					if(focus[0]-x==1&&pieces[focus[0]][focus[1]]==1){
						pieces[x][y]=pieces[focus[0]][focus[1]];
						pieces[focus[0]][focus[1]]=0;
					}
				}

				//Pawn to Queen
				if((pieces[x][y]==1&&x==0)||(pieces[x][y]==-1&&x==7)){
					pieces[x][y]*=6;
				}
			}
			
			//Knight
			if(pieces[focus[0]][focus[1]]==3||pieces[focus[0]][focus[1]]==-3){
				if((abs(focus[0]-x)==2&&abs(focus[1]-y)==1)||(abs(focus[0]-x)==1&&abs(focus[1]-y)==2)){
					pieces[x][y]=pieces[focus[0]][focus[1]];
					pieces[focus[0]][focus[1]]=0;
				}
			}
			
			//King
			if(pieces[focus[0]][focus[1]]==5||pieces[focus[0]][focus[1]]==-5){
				if((abs(focus[0]-x)==1&&abs(focus[1]-y)==1)||(abs(focus[0]-x)==1&&focus[1]==y)||(abs(focus[1]-y)==1&&focus[0]==x)){
					pieces[x][y]=pieces[focus[0]][focus[1]];
					pieces[focus[0]][focus[1]]=0;
				}
			}
			
			//Rook
			if(pieces[focus[0]][focus[1]]==2||pieces[focus[0]][focus[1]]==-2){
				if(align(x,y,focus[0],focus[1])){
					pieces[x][y]=pieces[focus[0]][focus[1]];
					pieces[focus[0]][focus[1]]=0;
				}
			}
			
			//Bishop
			if(pieces[focus[0]][focus[1]]==4||pieces[focus[0]][focus[1]]==-4){
				if(diagonal(x,y,focus[0],focus[1])){
					pieces[x][y]=pieces[focus[0]][focus[1]];
					pieces[focus[0]][focus[1]]=0;
				}
			}
			
			//Queen
			if(pieces[focus[0]][focus[1]]==6||pieces[focus[0]][focus[1]]==-6){
				if(align(x,y,focus[0],focus[1])){
					pieces[x][y]=pieces[focus[0]][focus[1]];
					pieces[focus[0]][focus[1]]=0;
				}else if(diagonal(x,y,focus[0],focus[1])){
					pieces[x][y]=pieces[focus[0]][focus[1]];
					pieces[focus[0]][focus[1]]=0;
				}
			}
			
			//Refresh
			focus[0]=-1;
			focus[1]=-1;
			build();
			
			//Toggle Player
			if(!equal(pieces,compare)){
				blackTurn=!blackTurn;
			}
		}
	}
	
	public boolean diagonal(int x1,int y1,int x2,int y2){
		if(abs(x2-x1)==abs(y2-y1)){
			if(x1<x2&&y1>y2){ //Quadrant I
				int j=y2+1;
				for(int i=x2-1;i>x1;i--){
					if(pieces[i][j]!=0) return false;
					j++;
				}
				return true;
			}
			if(x1<x2&&y1<y2){ //Quadrant II
				int j=y2-1;
				for(int i=x2-1;i>x1;i--){
					if(pieces[i][j]!=0) return false;
					j--;
				}
				return true;
			}
			if(x1>x2&&y1<y2){ //Quadrant III
				int j=y2-1;
				for(int i=x2+1;i<x1;i++){
					if(pieces[i][j]!=0) return false;
					j--;
				}
				return true;
			}
			if(x1>x2&&y1>y2){ //Quadrant IV
				int j=y2+1;
				for(int i=x2+1;i<x1;i++){
					if(pieces[i][j]!=0) return false;
					j++;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean align(int x1,int y1,int x2,int y2){
		if(y1>y2){
			int temp=y1;
			y1=y2;
			y2=temp;
		}
		if(x1>x2){
			int temp=x1;
			x1=x2;
			x2=temp;
		}
		if(x1==x2){
			for(int i=y1+1;i<y2;i++){
				if(pieces[x1][i]!=0) return false;
			}
			return true;
		}
		if(y1==y2){
			for(int i=x1+1;i<x2;i++){
				if(pieces[i][y1]!=0) return false;
			}
			return true;
		}
		return false;
	}
	
	public int abs(int x){
		return Math.abs(x);
	}
	
	public boolean equal(int[][] x,int[][] y){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(x[i][j]!=y[i][j]) return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args){
        launch(args);
    }
}
