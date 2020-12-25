package com.ics.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  生成4位随机验证码
 *
 */
public class ValidCode extends HttpServlet {
	
	private static final long serialVersionUID = 7984643570657748110L;

	private static final int generationStrategy = 2; //验证码生成策略，1纯数字,2纯字母,3或other则生成a-z A-Z 0-9随机码
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ImageIO.write(getImage(request), "JPEG", response.getOutputStream());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private BufferedImage getImage(HttpServletRequest request) {
		int width = 80, height = 39;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		g.setColor(new Color(0xf4f5f9));
		g.fillRect(0, 0, width, height);

		g.setColor(new Color(0xa5a5a5));
		g.drawRect(0, 0, width - 1, height - 1);
		String rand = StringUtil.getRandomStr(4,generationStrategy);
		g.setColor(new Color(0x121b24));
		String numberStr = rand;
		g.setFont(new Font("Atlantic Inline", Font.PLAIN, 20));
		String Str = numberStr.substring(0, 1);

		g.drawString(Str, 8, 27);

		Str = numberStr.substring(1, 2);
		g.drawString(Str, 25, 27);
		Str = numberStr.substring(2, 3);
		g.drawString(Str, 45, 27);

		Str = numberStr.substring(3, 4);
		g.drawString(Str, 65, 27);

		Random random = new Random();
		g.setColor(new Color(0x6eaae4));
		for (int i = 0; i < 150; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}
		
        g.setColor(Color.gray);
        for (int i = 0; i < 2; i++) {
            g.drawLine(random.nextInt(width),random.nextInt(width),random.nextInt(width),random.nextInt(width));
        }
        
		g.dispose();
		request.getSession().setAttribute("validCode", numberStr);
		return image;
	}

}
