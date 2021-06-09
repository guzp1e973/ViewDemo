package com.example.cnblog.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Comment;

public class CommentSaxHandler extends DefaultHandler {
	private Comment comment;
	private String tag;
	StringBuffer sbuff;
	// private boolean isFeed;
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		sbuff=new StringBuffer();
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		tag = qName;
		sbuff.delete(0, sbuff.length());
		if (qName.equals("entry")) {
			//isFeed = true;
			
			comment = new Comment();
		} else if (qName.equals("feed")) {
			//isFeed = false;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		tag = "";
		if (qName.equals("entry")) {
			AppStatic.comments.add(comment);
		}
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		sbuff.append(ch, start, length);
		String s = sbuff.toString();
		
		if (tag.equals("published")) {
			comment.setPublish(s);
		} else if (tag.equals("name")) {
			
			comment.setName(s);
		} else if (tag.equals("content")) {
			comment.setContent(s);
			//Log.i("msg", s);
		}
	}

}
