import java.awt.*;
import javax.swing.*;

public class ChessView extends JPanel{

	private JFrame window;
	private Board board;

	public ChessView(Board currBoard){
		window = new JFrame();
		window.setContentPane(this);
		window.setSize(800, 900);
		window.setVisible(true);
		setSize(800, 900);

		board = currBoard;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		System.out.println("Repainting");
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif", Font.BOLD, 50));

		Space cur = null;
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
					g.drawString(cur.getPiece().getSymbol() + "", 100*i, 100*(j+1));
				}
			}
		}
	}

}
