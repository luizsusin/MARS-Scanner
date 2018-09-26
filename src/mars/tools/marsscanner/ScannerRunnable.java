/*
                    GNU LESSER GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.


  This version of the GNU Lesser General Public License incorporates
the terms and conditions of version 3 of the GNU General Public
License, supplemented by the additional permissions listed below.

  0. Additional Definitions.

  As used herein, "this License" refers to version 3 of the GNU Lesser
General Public License, and the "GNU GPL" refers to version 3 of the GNU
General Public License.

  "The Library" refers to a covered work governed by this License,
other than an Application or a Combined Work as defined below.

  An "Application" is any work that makes use of an interface provided
by the Library, but which is not otherwise based on the Library.
Defining a subclass of a class defined by the Library is deemed a mode
of using an interface provided by the Library.

  A "Combined Work" is a work produced by combining or linking an
Application with the Library.  The particular version of the Library
with which the Combined Work was made is also called the "Linked
Version".

  The "Minimal Corresponding Source" for a Combined Work means the
Corresponding Source for the Combined Work, excluding any source code
for portions of the Combined Work that, considered in isolation, are
based on the Application, and not on the Linked Version.

  The "Corresponding Application Code" for a Combined Work means the
object code and/or source code for the Application, including any data
and utility programs needed for reproducing the Combined Work from the
Application, but excluding the System Libraries of the Combined Work.

  1. Exception to Section 3 of the GNU GPL.

  You may convey a covered work under sections 3 and 4 of this License
without being bound by section 3 of the GNU GPL.

  2. Conveying Modified Versions.

  If you modify a copy of the Library, and, in your modifications, a
facility refers to a function or data to be supplied by an Application
that uses the facility (other than as an argument passed when the
facility is invoked), then you may convey a copy of the modified
version:

   a) under this License, provided that you make a good faith effort to
   ensure that, in the event an Application does not supply the
   function or data, the facility still operates, and performs
   whatever part of its purpose remains meaningful, or

   b) under the GNU GPL, with none of the additional permissions of
   this License applicable to that copy.

  3. Object Code Incorporating Material from Library Header Files.

  The object code form of an Application may incorporate material from
a header file that is part of the Library.  You may convey such object
code under terms of your choice, provided that, if the incorporated
material is not limited to numerical parameters, data structure
layouts and accessors, or small macros, inline functions and templates
(ten or fewer lines in length), you do both of the following:

   a) Give prominent notice with each copy of the object code that the
   Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the object code with a copy of the GNU GPL and this license
   document.

  4. Combined Works.

  You may convey a Combined Work under terms of your choice that,
taken together, effectively do not restrict modification of the
portions of the Library contained in the Combined Work and reverse
engineering for debugging such modifications, if you also do each of
the following:

   a) Give prominent notice with each copy of the Combined Work that
   the Library is used in it and that the Library and its use are
   covered by this License.

   b) Accompany the Combined Work with a copy of the GNU GPL and this license
   document.

   c) For a Combined Work that displays copyright notices during
   execution, include the copyright notice for the Library among
   these notices, as well as a reference directing the user to the
   copies of the GNU GPL and this license document.

   d) Do one of the following:

       0) Convey the Minimal Corresponding Source under the terms of this
       License, and the Corresponding Application Code in a form
       suitable for, and under terms that permit, the user to
       recombine or relink the Application with a modified version of
       the Linked Version to produce a modified Combined Work, in the
       manner specified by section 6 of the GNU GPL for conveying
       Corresponding Source.

       1) Use a suitable shared library mechanism for linking with the
       Library.  A suitable mechanism is one that (a) uses at run time
       a copy of the Library already present on the user's computer
       system, and (b) will operate properly with a modified version
       of the Library that is interface-compatible with the Linked
       Version.

   e) Provide Installation Information, but only if you would otherwise
   be required to provide such information under section 6 of the
   GNU GPL, and only to the extent that such information is
   necessary to install and execute a modified version of the
   Combined Work produced by recombining or relinking the
   Application with a modified version of the Linked Version. (If
   you use option 4d0, the Installation Information must accompany
   the Minimal Corresponding Source and Corresponding Application
   Code. If you use option 4d1, you must provide the Installation
   Information in the manner specified by section 6 of the GNU GPL
   for conveying Corresponding Source.)

  5. Combined Libraries.

  You may place library facilities that are a work based on the
Library side by side in a single library together with other library
facilities that are not Applications and are not covered by this
License, and convey such a combined library under terms of your
choice, if you do both of the following:

   a) Accompany the combined library with a copy of the same work based
   on the Library, uncombined with any other library facilities,
   conveyed under the terms of this License.

   b) Give prominent notice with the combined library that part of it
   is a work based on the Library, and explaining where to find the
   accompanying uncombined form of the same work.

  6. Revised Versions of the GNU Lesser General Public License.

  The Free Software Foundation may publish revised and/or new versions
of the GNU Lesser General Public License from time to time. Such new
versions will be similar in spirit to the present version, but may
differ in detail to address new problems or concerns.

  Each version is given a distinguishing version number. If the
Library as you received it specifies that a certain numbered version
of the GNU Lesser General Public License "or any later version"
applies to it, you have the option of following the terms and
conditions either of that published version or of any later version
published by the Free Software Foundation. If the Library as you
received it does not specify a version number of the GNU Lesser
General Public License, you may choose any version of the GNU Lesser
General Public License ever published by the Free Software Foundation.

  If the Library as you received it specifies that a proxy can decide
whether future versions of the GNU Lesser General Public License shall
apply, that proxy's public statement of acceptance of any version is
permanent authorization for you to choose that version for the
Library.
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
import javax.swing.JCheckBox;
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
import mars.tools.marsscanner.util.Orientation;

/**
 * @author Luiz H. Susin
 * @date 14/09/2018
 * @time 12:51 AM
 * */
public class ScannerRunnable extends JFrame implements Runnable
{
	private static final long serialVersionUID = 2512752452188107581L;
	
	private static final int GRAPHIC_WIDTH = 696;
    private static final int GRAPHIC_HEIGHT = 512;
    
    private JPanel contentPane;
	
	private static JFormattedTextField verticalSize;
    private static JFormattedTextField horizontalSize;
    
    private static File imageFile = null;
    private static BufferedImage image = null;
    private static List<Point> tracePoints = new ArrayList<Point>();
    
    private static ScannerRunnable instance;
	
    /**
     * The frame and image processor
     * */
	public ScannerRunnable() throws Exception 
	{
		setTitle("MARS Scanner");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 290, 295);
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
		btnCalculateAndSend.setBounds(10, 197, 264, 23);
		contentPane.add(btnCalculateAndSend);
		
		final JFileChooser fChooser = new JFileChooser();
		fChooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de Imagem", "jpg", "jpeg", "jpe"));
		fChooser.setAcceptAllFileFilterUsed(false);
		
		JButton btnOpenFile = new JButton("Open file");
		btnOpenFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOpenFile.setBounds(116, 42, 158, 23);
		contentPane.add(btnOpenFile);
		
		JCheckBox chkInvert = new JCheckBox("Inverter Scan (Mark point when RGB > 245)");
		chkInvert.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkInvert.setHorizontalAlignment(SwingConstants.CENTER);
		chkInvert.setBounds(10, 167, 264, 23);
		contentPane.add(chkInvert);
		
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
		separator_1.setBounds(0, 228, 290, 5);
		contentPane.add(separator_1);
		
		JLabel lblMarsScannerCredits = new JLabel("MARS Scanner for MARS Bot");
		lblMarsScannerCredits.setForeground(Color.BLACK);
		lblMarsScannerCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarsScannerCredits.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblMarsScannerCredits.setBounds(10, 233, 264, 14);
		contentPane.add(lblMarsScannerCredits);
		
		JLabel lblCredits = new JLabel("Developed by Luiz H. Susin (@luizsusin)");
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits.setBounds(10, 248, 264, 14);
		contentPane.add(lblCredits);
		
		btnOpenFile.addActionListener(new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	int returnVal = fChooser.showOpenDialog(instance);
            	
            	if(returnVal == JFileChooser.APPROVE_OPTION)
            	{
            		try 
            		{
            			if(ImageIO.read(fChooser.getSelectedFile()).getWidth() <= Integer.valueOf(horizontalSize.getText())
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
            	
            	if(image != null)
            	{
	            	int coefAligX = 1, coefAligY = 0;
	            	
	            	if(horizontalAligment.getSelectedItem().equals(Orientation.HorizontalOrientation.CENTER))
	            		coefAligX = (Integer.valueOf(horizontalSize.getText()) - image.getWidth()) / 2;
	            	
	            	if(horizontalAligment.getSelectedItem().equals(Orientation.HorizontalOrientation.RIGHT))
	            		coefAligX = Integer.valueOf(horizontalSize.getText()) - image.getWidth();
	            	
	            	if(verticalAligment.getSelectedItem().equals(Orientation.VerticalOrientation.DOWN))
	            		coefAligY = Integer.valueOf(verticalSize.getText()) - image.getHeight();
	            	
	            	if(verticalAligment.getSelectedItem().equals(Orientation.VerticalOrientation.CENTER))
	            		coefAligY = (Integer.valueOf(verticalSize.getText()) - image.getHeight()) / 2;
	            	
	            	for(int y = 0; y < image.getHeight(); y++)
	            	{
	            		for(int x = 0; x < image.getWidth(); x++)
	            		{
	            			Color cPx = new Color(image.getRGB(x,y)); 
	            		
	            			if(chkInvert.isSelected())
	            			{
	            				if(cPx.getBlue() >= 245 && cPx.getGreen() >= 245 && cPx.getRed() >= 245)
	            					tracePoints.add(new Point(x + coefAligX, y + coefAligY));
	            			}
	            			else
	            			{
	            				if(cPx.getBlue() < 245 && cPx.getGreen() < 245 && cPx.getRed() < 245)
	            					tracePoints.add(new Point(x + coefAligX, y + coefAligY));
	            			}
	            		}
	            		
	            		if(y + 1 < image.getHeight())
	            		{
		            		y++;
		            		
		            		for(int x = image.getWidth() - 1; x >= 0; x--)
		            		{
		            			Color cPx = new Color(image.getRGB(x,y)); 
		            		
		            			if(chkInvert.isSelected())
		            			{
		            				if(cPx.getBlue() >= 245 && cPx.getGreen() >= 245 && cPx.getRed() >= 245)
		            					tracePoints.add(new Point(x + coefAligX, y + coefAligY));
		            			}
		            			else
		            			{
		            				if(cPx.getBlue() < 245 && cPx.getGreen() < 245 && cPx.getRed() < 245)
		            					tracePoints.add(new Point(x + coefAligX, y + coefAligY));
		            			}
		            		}
	            		}
	            	}
	            	
	            	tracePoints.add(new Point(-1, -1));
	            	
	            	btnCalculateAndSend.setText("Enviando...");
	            	
	            	try 
	            	{
	            		Globals.memory.setWord(MARSScanner.ADDR_STARTX, coefAligX);
	            		
						Globals.memory.setWord(MARSScanner.ADDR_NEXTX, tracePoints.get(0).x);
						Globals.memory.setWord(MARSScanner.ADDR_NEXTY, tracePoints.get(0).y);
						
						tracePoints.remove(0);
					} 
	            	catch (AddressErrorException e1) { e1.printStackTrace(); }
	            	
	            	btnCalculateAndSend.setText("Recalcular e Reenviar");
            	}
            }
        });
	}

	/**
	 * Exhibit the instance frame
	 * */
	@Override
	public void run() 
	{
		try 
		{ 
			instance.setVisible(true);
		} 
		catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * @return The Trace Point List with all the points scanned and marked
	 */
	public List<Point> getTracePoints()
	{
		return tracePoints;
	}
}
