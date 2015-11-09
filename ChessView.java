import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;


public class ChessView extends JPanel{

	private JFrame window;
	private BufferedImage chessBackground=new BufferedImage(1,1,
		BufferedImage.TYPE_BYTE_BINARY);
	private Board board;

	public ChessView(Board currBoard){
		window = new JFrame();
		window.setContentPane(this);
		window.setSize(800, 900);
		window.setVisible(true);
		setSize(800, 900);

		try{
			chessBackground = ImageIO.read(new File("empty_board.jpg"));
		}catch(Exception e){}
		board = currBoard;
    repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif", Font.BOLD, 50));
		if(chessBackground != null){
			g.drawImage(chessBackground, 0, 0, 800, 800, this);
		}

		Space cur = null;
		if(board != null && board.getSpacesArray() != null){
    Space[][] arr = board.getSpacesArray();

		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				cur = arr[7-j][i];
				if(cur.getPiece() != null){
					if(cur.getPiece().getTeam() == 'w'){
						g.setColor(Color.BLUE);
					}else{
						g.setColor(Color.BLACK);
					}
					g.drawString(cur.getPiece().getSymbol() + "", 100*i+35, 100*(j+1)-25);
				}
			}
		}
    }
	}

}
