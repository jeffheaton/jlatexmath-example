/*
 * Simple Java LaTex example
 * http://www.heatonresearch.com/
 * 
 * Copyright 2013 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */

package com.jeffheaton.latex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class LatexExample extends JFrame implements ActionListener {
	
	private JTextArea latexSource;
	private JButton btnRender;
	private JPanel drawingArea;

	public LatexExample() {
		this.setTitle("JLatexMath Example");
		this.setSize(500, 500);
		Container content = this.getContentPane();
		content.setLayout(new GridLayout(2, 1));
		this.latexSource = new JTextArea();
		
		JPanel editorArea = new JPanel();
		editorArea.setLayout(new BorderLayout());
		editorArea.add(new JScrollPane(this.latexSource),BorderLayout.CENTER);
		editorArea.add(btnRender = new JButton("Render"),BorderLayout.SOUTH);		
		
		content.add(editorArea);
		content.add(this.drawingArea = new JPanel());		
		this.btnRender.addActionListener(this);
		
		this.latexSource.setText("x=\\frac{-b \\pm \\sqrt {b^2-4ac}}{2a}");
	}

	public void render() {
		try {
			// get the text
			String latex = this.latexSource.getText();
			
			// create a formula
			TeXFormula formula = new TeXFormula(latex);
			
			// render the formla to an icon of the same size as the formula.
			TeXIcon icon = formula
					.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
			
			// insert a border 
			icon.setInsets(new Insets(5, 5, 5, 5));

			// now create an actual image of the rendered equation
			BufferedImage image = new BufferedImage(icon.getIconWidth(),
					icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = image.createGraphics();
			g2.setColor(Color.white);
			g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
			JLabel jl = new JLabel();
			jl.setForeground(new Color(0, 0, 0));
			icon.paintIcon(jl, g2, 0, 0);
			// at this point the image is created, you could also save it with ImageIO
			
			// now draw it to the screen			
			Graphics g = this.drawingArea.getGraphics();
			g.drawImage(image,0,0,null);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
					JOptionPane.INFORMATION_MESSAGE);		
		}

	}

	public static void main(String[] args) {
		LatexExample frame = new LatexExample();		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.btnRender ) {
			render();
		}
		
	}
}
