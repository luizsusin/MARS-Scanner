/**
 * MARS Scanner
 * Copyright (C) 2018 Luiz H. Susin [https://github.com/luizsusin/MARS-Scanner]
 *
 * Developed to be used with MARS (MIPS Assembler and Runtime Simulator)
 * Copyright (c) 2003-2013, Pete Sanderson and Kenneth Vollmar
 *
 * This program is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package mars.tools.marsscanner;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import mars.Globals;
import mars.mips.hardware.AddressErrorException;
import mars.tools.MARSScanner;
import mars.tools.marsscanner.util.EvoPoint;
import mars.tools.marsscanner.util.Orientation;

/**
 * @author Luiz H. Susin
 * @date 14/09/2018
 * @time 12:51 AM
 */
public class ScannerRunnable extends JFrame implements Runnable 
{
	private static final long serialVersionUID = 2512752452188107581L;

	/** Default MARS Evo workspace width */
	private static final int GRAPHIC_WIDTH = 512;
	/** Default MARS Evo workspace height */
	private static final int GRAPHIC_HEIGHT = 480;

	private JPanel contentPane;

	private static JFormattedTextField verticalSize;
	private static JFormattedTextField horizontalSize;

	private static File imageFile = null;
	private static BufferedImage image = null;
	
	/** Trace points list to send to MARS MMIO */
	private static List<EvoPoint> tracePoints = new ArrayList<EvoPoint>();

	private static ScannerRunnable instance;

	/**
	 * The frame and image processor
	 */
	public ScannerRunnable() throws Exception 
	{
		setTitle("MARS Scanner " + MARSScanner.getToolVersion());
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 300, 284);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		instance = this;

		JLabel lblMarsScanner = new JLabel("MARS Scanner");
		lblMarsScanner.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMarsScanner.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarsScanner.setBounds(10, 11, 264, 20);
		contentPane.add(lblMarsScanner);

		JLabel lblLoadImage = new JLabel("Load image [.JPG]:");
		lblLoadImage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLoadImage.setBounds(10, 46, 119, 14);
		contentPane.add(lblLoadImage);

		JButton btnCalculateAndSend = new JButton("Calculate and Send");
		btnCalculateAndSend.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCalculateAndSend.setEnabled(false);
		btnCalculateAndSend.setBounds(10, 174, 264, 23);
		contentPane.add(btnCalculateAndSend);

		final JFileChooser fChooser = new JFileChooser();
		fChooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de Imagem", "jpg", "jpeg", "jpe"));
		fChooser.setAcceptAllFileFilterUsed(false);

		JButton btnOpenFile = new JButton("Open file");
		btnOpenFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOpenFile.setBounds(116, 42, 158, 23);
		contentPane.add(btnOpenFile);

		JLabel lblHorizontalSize = new JLabel("Horizontal Size [X]:");
		lblHorizontalSize.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHorizontalSize.setBounds(10, 71, 119, 14);
		contentPane.add(lblHorizontalSize);

		JLabel lblVerticalSize = new JLabel("Vertical Size [Y]:");
		lblVerticalSize.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVerticalSize.setBounds(10, 96, 119, 14);
		contentPane.add(lblVerticalSize);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 35, 264, 5);
		contentPane.add(separator);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		horizontalSize = new JFormattedTextField(formatter);
		horizontalSize.setFont(new Font("Tahoma", Font.PLAIN, 11));
		horizontalSize.setBounds(116, 68, 158, 20);
		horizontalSize.setText(String.valueOf(GRAPHIC_WIDTH));
		contentPane.add(horizontalSize);

		verticalSize = new JFormattedTextField(formatter);
		verticalSize.setFont(new Font("Tahoma", Font.PLAIN, 11));
		verticalSize.setBounds(116, 93, 158, 20);
		verticalSize.setText(String.valueOf(GRAPHIC_HEIGHT));
		contentPane.add(verticalSize);

		JLabel lblVerticalAlignment = new JLabel("Vertical Alignment:");
		lblVerticalAlignment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVerticalAlignment.setBounds(10, 121, 119, 14);
		contentPane.add(lblVerticalAlignment);

		JLabel lblHorizontalAlignment = new JLabel("Horizontal Alignment:");
		lblHorizontalAlignment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHorizontalAlignment.setBounds(10, 146, 119, 14);
		contentPane.add(lblHorizontalAlignment);

		JComboBox<String> verticalAligment = new JComboBox<String>();
		verticalAligment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		verticalAligment.setBounds(116, 118, 158, 20);
		verticalAligment.addItem(Orientation.VerticalOrientation.UP);
		verticalAligment.addItem(Orientation.VerticalOrientation.CENTER);
		verticalAligment.addItem(Orientation.VerticalOrientation.DOWN);
		verticalAligment.setSelectedItem(Orientation.VerticalOrientation.CENTER);
		contentPane.add(verticalAligment);

		JComboBox<String> horizontalAligment = new JComboBox<String>();
		horizontalAligment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		horizontalAligment.setBounds(116, 143, 158, 20);
		horizontalAligment.addItem(Orientation.HorizontalOrientation.LEFT);
		horizontalAligment.addItem(Orientation.HorizontalOrientation.CENTER);
		horizontalAligment.addItem(Orientation.HorizontalOrientation.RIGHT);
		horizontalAligment.setSelectedItem(Orientation.HorizontalOrientation.CENTER);
		contentPane.add(horizontalAligment);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.GRAY);
		separator_1.setBounds(0, 205, 290, 5);
		contentPane.add(separator_1);

		JLabel lblMarsScannerCredits = new JLabel("MARS Scanner " + MARSScanner.getToolVersion());
		lblMarsScannerCredits.setForeground(Color.BLACK);
		lblMarsScannerCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarsScannerCredits.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblMarsScannerCredits.setBounds(10, 210, 264, 14);
		contentPane.add(lblMarsScannerCredits);

		JLabel lblCredits = new JLabel("<html>Developed by Luiz H. Susin (<a href=\"https://github.com/luizsusin\" target=\"_blank\">@luizsusin</a>)</html>");
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits.setBounds(10, 225, 264, 16);
		contentPane.add(lblCredits);

		btnOpenFile.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int returnVal = fChooser.showOpenDialog(instance);

				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					try 
					{
						if (ImageIO.read(fChooser.getSelectedFile()).getWidth() <= Integer.valueOf(horizontalSize.getText())
						&& ImageIO.read(fChooser.getSelectedFile()).getHeight() <= Integer.valueOf(verticalSize.getText())) 
						{
							imageFile = fChooser.getSelectedFile();

							btnOpenFile.setText(imageFile.getName());
							btnCalculateAndSend.setEnabled(true);
							btnCalculateAndSend.setText("Calculate and Send");
						} 
						else JOptionPane.showMessageDialog(null, "The image is bigger than the workspace.", "Error", JOptionPane.ERROR_MESSAGE);
					} 
					catch (Exception e1) { e1.printStackTrace(); }
				}
			}
		});

		btnCalculateAndSend.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				btnCalculateAndSend.setText("Calculating...");
				tracePoints.clear();

				try { image = ImageIO.read(imageFile); } 
				catch (IOException e1) { e1.printStackTrace(); }

				if (image != null) 
				{
					int coefAligX = 0, coefAligY = 0;

					if (horizontalAligment.getSelectedItem().equals(Orientation.HorizontalOrientation.CENTER))
						coefAligX = (Integer.valueOf(horizontalSize.getText()) - image.getWidth()) / 2;

					if (horizontalAligment.getSelectedItem().equals(Orientation.HorizontalOrientation.RIGHT))
						coefAligX = Integer.valueOf(horizontalSize.getText()) - image.getWidth();

					if (verticalAligment.getSelectedItem().equals(Orientation.VerticalOrientation.DOWN))
						coefAligY = Integer.valueOf(verticalSize.getText()) - image.getHeight();

					if (verticalAligment.getSelectedItem().equals(Orientation.VerticalOrientation.CENTER))
						coefAligY = (Integer.valueOf(verticalSize.getText()) - image.getHeight()) / 2;

					for (int y = 0; y < image.getHeight(); y++) 
					{
						for (int x = 0; x < image.getWidth(); x++)
						{
							Color cPx = new Color(image.getRGB(x, y));
							
							if(!cPx.equals(Color.WHITE))
								tracePoints.add(new EvoPoint(new Point(x + coefAligX, y + coefAligY), cPx));
						}
						
						if (y + 1 < image.getHeight()) 
						{
							y++;

							for (int x = image.getWidth() - 1; x >= 0; x--)
							{
								Color cPx = new Color(image.getRGB(x, y));
								
								if(!cPx.equals(Color.WHITE))
									tracePoints.add(new EvoPoint(new Point(x + coefAligX, y + coefAligY), cPx));
							}
						}
					}

					tracePoints.add(new EvoPoint(new Point(-1, -1), Color.WHITE));

					btnCalculateAndSend.setText("Sending...");

					try 
					{
						Globals.memory.setWord(MARSScanner.ADDR_NEXTX, (int) tracePoints.get(0).getPosition().getX());
						Globals.memory.setWord(MARSScanner.ADDR_NEXTY, (int) tracePoints.get(0).getPosition().getY());

						tracePoints.remove(0);
					} 
					catch (AddressErrorException e1) { e1.printStackTrace(); }

					btnCalculateAndSend.setText("Recalculate and Resend");
				}
			}
		});
	}

	/**
	 * Exhibit the instance frame
	 */
	@Override
	public void run() 
	{
		try { instance.setVisible(true); } 
		catch (Exception e) { e.printStackTrace(); }
	}

	/**
	 * @return The Trace Point List with all the points scanned and marked
	 */
	public List<EvoPoint> getTracePoints() 
	{
		return tracePoints;
	}
}
