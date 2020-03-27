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
		Font font = new Font("宋体",Font.PLAIN,20);
		InitGlobalFont(font);
		JFrame frame = new JFrame("线性拟合");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		JButton bSelect = new JButton("选择文件");
		panel.add(bSelect);
		
		FileDialog fd = new FileDialog(frame,"打开文件",FileDialog.LOAD);		
		bSelect.addActionListener(e->{
			fd.setVisible(true);
			fileName = fd.getDirectory()+fd.getFile();
			
		});
		
		JButton bGenerate = new JButton("生成拟合图形");
		panel.add(bGenerate);
		
		bGenerate.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent arg0) {
            	if (fileName.equals("nullnull")) {
            		JOptionPane.showMessageDialog(
                            frame,
                            "请选中文件",
                            "文件是空",
                            JOptionPane.WARNING_MESSAGE
                    );
    			}else {
    				LeastSquareDrawLineScatter dls = new LeastSquareDrawLineScatter("工程数据线性拟合", fileName);
    				dls.pack();
    		        RefineryUtilities.centerFrameOnScreen(dls);
    		        dls.setVisible(true);
    		        dls.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    				kLabel.setText("拟合直线的k值：" + dls.getK());
    				bLabel.setText("拟合直线的b值：" + dls.getB());
    			}
            }
        });
		
		kLabel = new JLabel();
		bLabel = new JLabel();
		kLabel.setText("无数据");
		bLabel.setText("无数据");
		
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