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

@SuppressWarnings("serial")
public class ScannerRunnable extends JFrame implements Runnable
{
	private static final int GRAPHIC_WIDTH = 696;
    private static final int GRAPHIC_HEIGHT = 512;
    
    private JPanel contentPane;
	
	private static JFormattedTextField verticalSize;
    private static JFormattedTextField horizontalSize;
    
    private static File imageFile = null;
    private static BufferedImage image = null;
    private static List<Point> tracePoints = new ArrayList<Point>();
    
    private static ScannerRunnable instance;
	
	public ScannerRunnable() throws Exception 
	{
		setTitle("MARS Scanner");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 290, 303);
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
		
		JLabel lblCarregarImagemjpg = new JLabel("Carregar imagem [.JPG]:");
		lblCarregarImagemjpg.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCarregarImagemjpg.setBounds(10, 46, 119, 14);
		contentPane.add(lblCarregarImagemjpg);
		
		JButton btnCalculateAndSend = new JButton("Calcular e Enviar");
		btnCalculateAndSend.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCalculateAndSend.setEnabled(false);
		btnCalculateAndSend.setBounds(10, 197, 264, 23);
		contentPane.add(btnCalculateAndSend);
		
		final JFileChooser fChooser = new JFileChooser();
		fChooser.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de Imagem", "jpg", "jpeg", "jpe"));
		fChooser.setAcceptAllFileFilterUsed(false);
		
		JButton btnOpenFile = new JButton("Abrir arquivo");
		btnOpenFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOpenFile.setBounds(139, 42, 135, 23);
		contentPane.add(btnOpenFile);
		
		JCheckBox chkInvert = new JCheckBox("Inverter Impressão (Trace quando RGB > 245)");
		chkInvert.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkInvert.setHorizontalAlignment(SwingConstants.CENTER);
		chkInvert.setBounds(10, 167, 264, 23);
		contentPane.add(chkInvert);
		
		JLabel lblTamanhoVerticalx = new JLabel("Tamanho Horizontal [X]:");
		lblTamanhoVerticalx.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTamanhoVerticalx.setBounds(10, 71, 119, 14);
		contentPane.add(lblTamanhoVerticalx);
		
		JLabel lblTamanhoHorizontaly = new JLabel("Tamanho Vertical [Y]:");
		lblTamanhoHorizontaly.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTamanhoHorizontaly.setBounds(10, 96, 119, 14);
		contentPane.add(lblTamanhoHorizontaly);
		
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
		horizontalSize.setBounds(139, 68, 135, 20);
		horizontalSize.setText(String.valueOf(GRAPHIC_WIDTH));
		contentPane.add(horizontalSize);
		
	    verticalSize = new JFormattedTextField(formatter);
	    verticalSize.setFont(new Font("Tahoma", Font.PLAIN, 11));
	    verticalSize.setBounds(139, 93, 135, 20);
	    verticalSize.setText(String.valueOf(GRAPHIC_HEIGHT));
		contentPane.add(verticalSize);
		
		JLabel lblAlinhamentoVertical = new JLabel("Alinhamento Vertical:");
		lblAlinhamentoVertical.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAlinhamentoVertical.setBounds(10, 121, 119, 14);
		contentPane.add(lblAlinhamentoVertical);
		
		JLabel lblAlinhamentoHorizontal = new JLabel("Alinhamento Horizontal:");
		lblAlinhamentoHorizontal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAlinhamentoHorizontal.setBounds(10, 146, 119, 14);
		contentPane.add(lblAlinhamentoHorizontal);
		
		JComboBox<String> verticalAligment = new JComboBox<String>();
		verticalAligment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		verticalAligment.setBounds(139, 118, 135, 20);
		verticalAligment.addItem(Orientation.VerticalOrientation.CIMA);
		verticalAligment.addItem(Orientation.VerticalOrientation.CENTRO);
		verticalAligment.addItem(Orientation.VerticalOrientation.BAIXO);
		verticalAligment.setSelectedItem(Orientation.VerticalOrientation.CENTRO);
		contentPane.add(verticalAligment);
		
		JComboBox<String> horizontalAligment = new JComboBox<String>();
		horizontalAligment.setFont(new Font("Tahoma", Font.PLAIN, 11));
		horizontalAligment.setBounds(139, 143, 135, 20);
		horizontalAligment.addItem(Orientation.HorizontalOrientation.ESQUERDA);
		horizontalAligment.addItem(Orientation.HorizontalOrientation.CENTRO);
		horizontalAligment.addItem(Orientation.HorizontalOrientation.DIREITA);
		horizontalAligment.setSelectedItem(Orientation.HorizontalOrientation.CENTRO);
		contentPane.add(horizontalAligment);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 228, 284, 5);
		contentPane.add(separator_1);
		
		JLabel lblMarsScannerCredits = new JLabel("MARS Scanner for MARS Bot");
		lblMarsScannerCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarsScannerCredits.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 9));
		lblMarsScannerCredits.setBounds(6, 231, 268, 14);
		contentPane.add(lblMarsScannerCredits);
		
		JLabel lblCredits = new JLabel("Developed by Luiz H. Susin and Lissandra M. Fischer");
		lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits.setBounds(10, 244, 264, 14);
		contentPane.add(lblCredits);
		
		JLabel lblUfscBlumenau = new JLabel("UFSC Blumenau, 2018");
		lblUfscBlumenau.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUfscBlumenau.setHorizontalAlignment(SwingConstants.CENTER);
		lblUfscBlumenau.setBounds(10, 256, 264, 14);
		contentPane.add(lblUfscBlumenau);
		
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
							btnCalculateAndSend.setText("Calcular e Enviar");
						}
						else JOptionPane.showMessageDialog(null, "A Imagem carregada é maior que o espaço de trabalho.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            	btnCalculateAndSend.setText("Calculando...");
            	tracePoints.clear();
            	
            	try { image = ImageIO.read(imageFile); } 
            	catch (IOException e1) { e1.printStackTrace(); }
            	
            	if(image != null)
            	{
	            	int coefAligX = 1, coefAligY = 0;
	            	
	            	if(horizontalAligment.getSelectedItem().equals(Orientation.HorizontalOrientation.CENTRO))
	            		coefAligX = (Integer.valueOf(horizontalSize.getText()) - image.getWidth()) / 2;
	            	
	            	if(horizontalAligment.getSelectedItem().equals(Orientation.HorizontalOrientation.DIREITA))
	            		coefAligX = Integer.valueOf(horizontalSize.getText()) - image.getWidth();
	            	
	            	if(verticalAligment.getSelectedItem().equals(Orientation.VerticalOrientation.BAIXO))
	            		coefAligY = Integer.valueOf(verticalSize.getText()) - image.getHeight();
	            	
	            	if(verticalAligment.getSelectedItem().equals(Orientation.VerticalOrientation.CENTRO))
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
						Globals.memory.setWord(MARSScanner.ADDR_STARTY, coefAligY);
	            		
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

	@Override
	public void run() 
	{
		try 
		{ 
			instance.setVisible(true);
		} 
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public List<Point> getTracePoints()
	{
		return tracePoints;
	}
}
