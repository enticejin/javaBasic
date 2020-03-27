package com.jl.test.draw.jfree;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jfree.ui.RefineryUtilities;

public class LeastSquareWin {
	private String fileName = "nullnull";
	private JLabel kLabel = null;
	private JLabel bLabel = null;
	
	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
	    for (Enumeration<Object> keys = UIManager.getDefaults().keys();
	         keys.hasMoreElements(); ) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get(key);
	      if (value instanceof FontUIResource) {
	        UIManager.put(key, fontRes);
	      }
	    }
	}

	
	public void createWin() {
		Font font = new Font("����",Font.PLAIN,20);
		InitGlobalFont(font);
		JFrame frame = new JFrame("�������");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		JButton bSelect = new JButton("ѡ���ļ�");
		panel.add(bSelect);
		
		FileDialog fd = new FileDialog(frame,"���ļ�",FileDialog.LOAD);		
		bSelect.addActionListener(e->{
			fd.setVisible(true);
			fileName = fd.getDirectory()+fd.getFile();
			
		});
		
		JButton bGenerate = new JButton("�������ͼ��");
		panel.add(bGenerate);
		
		bGenerate.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent arg0) {
            	if (fileName.equals("nullnull")) {
            		JOptionPane.showMessageDialog(
                            frame,
                            "��ѡ���ļ�",
                            "�ļ��ǿ�",
                            JOptionPane.WARNING_MESSAGE
                    );
    			}else {
    				LeastSquareDrawLineScatter dls = new LeastSquareDrawLineScatter("���������������", fileName);
    				dls.pack();
    		        RefineryUtilities.centerFrameOnScreen(dls);
    		        dls.setVisible(true);
    		        dls.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    				kLabel.setText("���ֱ�ߵ�kֵ��" + dls.getK());
    				bLabel.setText("���ֱ�ߵ�bֵ��" + dls.getB());
    			}
            }
        });
		
		kLabel = new JLabel();
		bLabel = new JLabel();
		kLabel.setText("������");
		bLabel.setText("������");
		
		panel.add(kLabel);
		panel.add(bLabel);
		
		frame.add(panel);
		frame.setBounds(40, 40, 800, 300);
		frame.setVisible(true);
		// close the windows
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	
	public static void main(String[] args) {
		LeastSquareWin lsw = new LeastSquareWin();
		lsw.createWin();
	}
	
}