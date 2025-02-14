/*
 * Copyright 2015-2020 Ray Fowler
 * 
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://www.gnu.org/licenses/gpl-3.0.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rotp.ui.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import rotp.ui.BasePanel;


public class HelpUI extends BasePanel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private static final Color backgroundHaze = new Color(0,0,0,40);
    private static final int FONT_SIZE		= 16;
    private static final int MIN_FONT_SIZE	= 10;
    private final Color blueBackC  = new Color(78,101,155);
    private final Color brownBackC = new Color(240,240,240);
    private final Color brownTextC = new Color(45,14,5);
    
    private List<HelpSpec> specs = new ArrayList<>();
    private BasePanel parent;
    
    public HelpUI() {
        init();
    }
    private void init() {
        setOpaque(false);
        addMouseListener(this);
    }
    public void open(BasePanel p) {
        parent = p;
        enableGlassPane(this);
    }
    public void close() {
        specs.clear();
        disableGlassPane();
    }
    public void clear() {
        specs.clear();
    }
    
    public HelpSpec addBrownHelpText(int x, int y, int w, int num, String text) {
        HelpSpec sp = addBlueHelpText(x,y,w,num,text);
        sp.backC = brownBackC;
        sp.textC = brownTextC;
        return sp;
    }
    public HelpSpec addBlueHelpText(int x, int y, int w, int num, String text) {
        return addBlueHelpText(x,y,w,num,text,-1,-1,-1,-1,-1,-1);
    }
    public HelpSpec addBlueHelpText(int x, int y, int w, int num, String text, int x1, int y1, int x2, int y2) {
        return addBlueHelpText(x,y,w,num,text,x1,y1,x2,y2,-1,-1);
    }
    public HelpSpec addBlueHelpText(int x, int y, int w, int num, String text, int x1, int y1, int x2, int y2, int x3, int y3) {
        HelpSpec sp = new HelpSpec();
        if (num==0)
        	sp.lines = getLineNumber(text, w);
        else
        	sp.lines = num;
 
        if (y<0)
        	sp.y = -y - sp.height();
        else
        	sp.y = y;
        sp.x = x;
        sp.w = w;
        	
        sp.x1 = x1;
        sp.y1 = y1;
        sp.x2 = x2;
        sp.y2 = y2;
        sp.x3 = x3;
        sp.y3 = y3;
        sp.text = text;
        sp.backC = blueBackC;
        specs.add(sp);
        return sp;
    }
    public int getLineNumber(String str, int maxWidth) {
    	Graphics g = getGraphics();
        int fontSize = FONT_SIZE;
        g.setFont(narrowFont(fontSize));
        List<String> lines = wrappedLines(g, str, maxWidth - s30);
        g.dispose();
        return lines.size();
    }
    @Override
    public void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        
        int w = getWidth();
        int h = getHeight();
        Graphics2D g = (Graphics2D) g0;
        g.setColor(backgroundHaze);
        g.fillRect(0, 0, w, h);

        for (HelpSpec spec: specs) {
            int lineH = lineH();
            // draw background box
            Color backC = spec.backC;
            Color bdrC = new Color(backC.getRed(), backC.getGreen(), backC.getBlue(), 160);
            int specH = spec.height();
            g.setColor(bdrC);
            g.fillRect(spec.x, spec.y, spec.w, specH);
            g.setColor(backC);
            g.fillRect(spec.x+s5, spec.y+s5, spec.w-s10, specH-s10);
            // draw box text
            g.setColor(spec.textC);
            int fontSize = FONT_SIZE;
            g.setFont(narrowFont(fontSize));
            List<String> lines = this.wrappedLines(g, spec.text, spec.w - s30);
            while ((lines.size() > spec.lines) && (fontSize > MIN_FONT_SIZE)) {
                fontSize--;
                g.setFont(narrowFont(fontSize));
                lines = this.wrappedLines(g, spec.text, spec.w - s30);
            }
            int x0 = spec.x + s15;
            int y0 = spec.y + lineH+s7;
            for (String line: lines) {
                drawString(g,line, x0, y0);
                y0 += lineH;
            }
            // draw line to target
            if (spec.x2 >= 0) {
                Stroke prev = g.getStroke();
                g.setStroke(stroke2);
                g.setColor(spec.lineC);
                g.drawLine(spec.x1, spec.y1, spec.x2, spec.y2);
                if (spec.x3 >=0) 
                    g.drawLine(spec.x2, spec.y2, spec.x3, spec.y3);
                g.setStroke(prev);
            }
            // BR: draw lines of target Array
            if (spec.lineArr != null) {
                Stroke prev = g.getStroke();
                g.setStroke(stroke2);
                g.setColor(spec.lineC);
            	int size = spec.lineArr.length/2 - 1;
            	for (int i=0; i<size; i++) {
            		int k = 2*i;
            		g.drawLine(spec.lineArr[k], spec.lineArr[k+1], spec.lineArr[k+2], spec.lineArr[k+3]);
            	}
                g.setStroke(prev);
            }
       }
    }
    @Override
    public void keyPressed(KeyEvent e)		{
        switch(e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                parent.cancelHelp();
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                parent.advanceHelp();
                break;
			case KeyEvent.VK_L:
				if (e.isAltDown())
				reloadLabels();
				return;
        }
    }
    @Override
    public void mouseClicked(MouseEvent e)	{ }
    @Override
    public void mousePressed(MouseEvent e)	{ }
    @Override
    public void mouseReleased(MouseEvent e)	{ parent.advanceHelp(); }
    @Override
    public void mouseEntered(MouseEvent e)	{ }
    @Override
    public void mouseExited(MouseEvent e)	{ }
    static int lineH()				{ return s18; }
    static int height(int lines)		{ return s20 + lines*lineH(); }
 
    public class HelpSpec {
        private int x, y, w;
        private int lines;
        private int[] lineArr; // BR: to allow frames
        private int x1 = -1;
        private int y1 = -1;
        private int x2 = -1;
        private int y2 = -1;
        private int x3 = -1;
        private int y3 = -1;
        private Color backC = Color.blue;
        private Color textC = Color.white;
        private Color lineC = Color.white;
        private String text;
        public int height() { return HelpUI.height(lines); }
        public int x()	    { return x; }
        public int y()	    { return y; }
        public int xe()		{ return x + w; }
        public int ye()		{ return y + height(); }
        public void setLine(int x1, int y1, int x2, int y2) {
            setLine(x1,y1,x2,y2,-1,-1);
        }
        public void setLine(int x1a, int y1a, int x2a, int y2a, int x3a, int y3a) {
            x1 = x1a;
            y1 = y1a;
            x2 = x2a;
            y2 = y2a;
            x3 = x3a;
            y3 = y3a;
        }
        public void setLineArr(int... arr) {
        	lineArr = arr;
        }
        public int[] rect(int x, int y, int w, int h) {
        	return new int[] {x, y, x+w, y, x+w, y+h, x, y+h, x, y};
        }
    }    
}

