
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Socket socket = null;
	public BufferedReader in = null;
	public PrintWriter out = null;
	
	public static boolean isconnected = false;

	
	public Client() throws IOException {
		JFrame window = new JFrame("Multi-Server Client Noteboard");
		window.setSize(900, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		window.getContentPane().setLayout(null);
//		window.setVisible(true);
		
		
		JLabel ip = new JLabel("IP");
		ip.setBounds(10, 10, 50, 30);
		JTextField IPfield = new JTextField();
		IPfield.setBounds(40, 10, 150, 30);
	//	ip.setVisible(true);
		//IPfield.setVisible(true);
		
		JLabel port = new JLabel("Port #");
		port.setBounds(200, 10, 50, 30);
		JTextField portfield = new JTextField();
		portfield.setBounds(260, 10, 150, 30);
		//port.setVisible(true);
		//portfield.setVisible(true);
		
		window.add(ip);
		window.add(IPfield);
		window.add(ip);
		window.add(port);
		window.add(portfield);

			
		JButton connect = new JButton("CONNECT");
		connect.setBounds(500, 10, 100, 30);
		JButton discon = new JButton("DISCONNECT");
		//connect.setVisible(true);
		//discon.setVisible(false);
		discon.setBounds(500,10,150,30);
		
		window.getContentPane().add(connect);
		window.add(discon);
		
		JLabel x = new JLabel("X");
		JTextField xAxis = new JTextField();
		//x.setVisible(true);
		//xAxis.setVisible(true);
		x.setBounds(10, 50, 60, 30 );
		xAxis.setBounds(50, 50, 120, 30);
		window.add(x);
		window.add(xAxis);
		
		
		JLabel y = new JLabel("Y");
		JTextField yAxis = new JTextField();
	//	y.setVisible(true);
	//	yAxis.setVisible(true);
		y.setBounds(10, 90, 60, 30);
		yAxis.setBounds(50,100,120,30 );
		window.add(y);
		window.add(yAxis);
		
		JLabel widthlabel = new JLabel("Width");
		JTextField width = new JTextField();
		JLabel heightlabel = new JLabel("Height");
		JTextField height = new JTextField();
		widthlabel.setVisible(true);
	//	width.setVisible(true);
		widthlabel.setBounds(10, 140, 60, 30);
		width.setBounds(50, 150, 120, 30);
	//	widthlabel.setVisible(true);
	//	width.setVisible(true);
		window.add(width);
		window.add(widthlabel);
		
		//heightlabel.setVisible(true);
		//height.setVisible(true);
		heightlabel.setBounds(10, 200, 60, 30);
		height.setBounds(50, 200, 120, 30);
		window.add(height);
		window.add(heightlabel);
		
		JLabel colourLabel = new JLabel("Color");
		JComboBox<String> colourBox = new JComboBox<>();
		//colourLabel.setVisible(true);
		colourLabel.setBounds(10, 250, 60, 30);
	//	colourBox.setVisible(true);
		colourBox.setBounds(50, 250, 100, 30);
		colourBox.addItem("Color");
		window.add(colourBox);
		window.add(colourLabel);
		
		JLabel message = new JLabel("Message");
		//message.setVisible(true);
		message.setBounds(10, 300, 60, 30);
		JTextField messagefield = new JTextField();
		messagefield.setBounds(80, 300, 160, 30);
		//messagefield.setVisible(true);
		window.add(message);
		window.add(messagefield);
		
		JButton postbutton = new JButton("POST");
		//postbutton.setVisible(true);
		postbutton.setBounds(220, 60, 150, 30);
		window.add(postbutton);
		
		JButton getbutton = new JButton("GET");
	//	getbutton.setVisible(true);
		getbutton.setBounds(220, 110, 60, 30);
		window.add(getbutton);
		
		JButton getPin = new JButton("GET PINS");
		//getPin.setVisible(true);
		getPin.setBounds(290, 110, 100, 30);
		window.add(getPin);
		
		JButton pinbutton = new JButton("PIN");
	//	pinbutton.setVisible(true);
		pinbutton.setBounds(220, 150, 150, 30);
		window.add(pinbutton);
		
		JButton unpinbutton = new JButton("UNPIN");
	//	unpinbutton.setVisible(true);
		unpinbutton.setBounds(220, 190, 150, 30);
		window.add(unpinbutton);
		
		JButton clearbutton = new JButton("CLEAR");
	//	clearbutton.setVisible(true);
		clearbutton.setBounds(220, 250, 150, 30);
		window.add(clearbutton);
		
//		JButton connect = new JButton("CONNECT");
//		connect.setBounds(500, 10, 100, 30);
//		JButton discon = new JButton("DISCONNECT");
//		connect.setVisible(true);
//		discon.setVisible(true);
		
		JLabel resultslabel = new JLabel("RESULT");
		JTextArea results = new JTextArea();
//		results.setVisible(true);
		results.setEditable(false);
	//	resultslabel.setVisible(true);
		resultslabel.setBounds(450, 60, 80, 30);
		results.setBounds(450, 90, 400, 200);
		window.add(resultslabel);
		window.add(results);
		
		window.setVisible(true);
		port.setVisible(true);
		portfield.setVisible(true);
		ip.setVisible(true);
		IPfield.setVisible(true);
		connect.setVisible(true);
		discon.setVisible(false);
		x.setVisible(true);
		y.setVisible(true);
		width.setVisible(true);
		widthlabel.setVisible(true);
		width.setVisible(true);		
		heightlabel.setVisible(true);
		height.setVisible(true);
		colourBox.setVisible(true);
		colourLabel.setVisible(true);
		message.setVisible(true);
		messagefield.setVisible(true);
		getbutton.setVisible(true);
		postbutton.setVisible(true);
		getPin.setVisible(true);
		pinbutton.setVisible(true);
		unpinbutton.setVisible(true);
		clearbutton.setVisible(true);
		results.setVisible(true);
		resultslabel.setVisible(true);
		//
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				String event = a.getActionCommand();
				
				if (event.equals("CONNECT")) {
					try {
						int portNum = Integer.parseInt(portfield.getText());
						socket = new Socket(IPfield.getText(), portNum);
						//connect.setVisible(true);
						in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						out = new PrintWriter(socket.getOutputStream(),true);
						isconnected = true;
						results.setText(in.readLine());
						//colourBox.removeAllItems();;

						   while (in.ready()) {
					             colourBox.addItem(in.readLine());
					         }

						discon.setVisible(true);
						connect.setVisible(false);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						results.setText("Connected failed");
						//e.printStackTrace();
					}
				}
			}
		});
		//
		discon.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent a) {
				String event = a.getActionCommand();
				if (event.equals("DISCONNECT")) {
				try {	 
					//	socket.close();
					//	in.close();
						isconnected = false;
						out.println("DISCONNECT");
						//connect.setVisible(false);\
						discon.setVisible(false);
						connect.setVisible(true);
						results.setText(in.readLine());
						socket.close();
				} catch (IOException e1) {
					out.println("Disconnect failed");
				}
				}
				
			}
		});
		
		getbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				String event = a.getActionCommand();
				String string = "GET";
				if (event.equals("GET")) {
						if (!colourBox.getSelectedItem().equals("Color")) {
							string = string +" color=" +colourBox.getSelectedItem();
						}

						if (!xAxis.getText().equals("") && !yAxis.getText().equals("")) {
							String x = xAxis.getText();
							String y = yAxis.getText();
							string += " contains=" + x + " " + y;
						}
						
						if (!messagefield.getText().equals("")) {
							string += " refersTo=" + messagefield.getText();
						}
						if (string.equals("GET")) {
							results.setText("Error");
						}else {
							out.println(string);

							   String output = "";
				                 try {
									do {
									     try {
											output += in.readLine() + "\n";
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									 } while (in.ready());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				                 
				                 results.setText(output);
						
				}
				}
			}
		});
		
		//
		getPin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {				
				String event = a.getActionCommand();
				if (event.equals("GET PINS")) {
					out.println("GET PINS");
	                 String output = "";
	                 try {
						do {
						     try {
								output += in.readLine() + "\n";
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 } while (in.ready());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                 
	                 results.setText(output);
	
		                 
				}
				
			}
		});
	
	
	postbutton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent a) {
			// TODO Auto-generated method stub	
			String event = a.getActionCommand();
			if (event.equals("POST")) {
			String string = "POST ";
		
			if (!xAxis.getText().equals("") && !yAxis.getText().equals("") && !width.getText().equals("") && !height.getText().equals("")) {
				String postx = xAxis.getText();
				String posty = yAxis.getText();
				String postw = width.getText();
				String posth = height.getText();
				string = string + postx + " " + posty + " " + postw + " "  + posth;
				if (colourBox.getItemAt(0).equals("Color")) {
				string = string +" " +colourBox.getItemAt(1);
				} else {
					string = string + " " + colourBox.getSelectedItem();
				}
			string = string +" "+ messagefield.getText();
			//System.out.println(string);
			out.println(string);
			try {
				results.setText(in.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			} else {
				results.setText("Error.");
			}
//				results.setText(in.readLine());

			}
			
		}
		
	});
	pinbutton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String action = e.getActionCommand();


				if (action.equals("PIN")) {
					String postx = xAxis.getText();
					String posty = yAxis.getText();
					if (postx.equals("")&&posty.equals("")) {
						results.setText("Error");
					}else {
						out.println("PIN "+postx+" "+posty);
		                try {
							results.setText(in.readLine());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					
		}
		}
	});
	unpinbutton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String action = e.getActionCommand();
	
				if (action.equals("UNPIN")) {
					String postx = xAxis.getText();
					String posty = yAxis.getText();
					if (postx.equals("")&&posty.equals("")) {
						results.setText("Error");
					}else {
						out.println("UNPIN "+postx+" "+posty);
		                try {
							results.setText(in.readLine());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
		}
		}
	});
	clearbutton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String event = e.getActionCommand();
			if (event.equals("CLEAR")) {
				out.println("CLEAR");
				try {
					results.setText(in.readLine());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	}	
		}
		
	});
	
}
	
	public static void main(String args[]) throws IOException {
		Client window = new Client();
	
	}

	
//	@Override
//	public void actionPerformed(ActionEvent a) {
//		// TODO Auto-generated method stub
//		String event = a.getActionCommand();
//		
//	}

}
