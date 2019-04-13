/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0319
 * 
 * @history    Date        author         reason
 *             2011/03/19  Xuetao Liu     created                 
 **/

package com.blackberryappframework.Utility;

import net.rim.device.api.ui.Color;


public class ColorUtils {

	
    public static int getColorByName(String color) {

        int intColor = -1;
        
        String colorStr = color == null ? null : color.trim();
        if (colorStr == null || colorStr.length() == 0)
        	intColor = -1;
        else if (colorStr.equalsIgnoreCase("ALICEBLUE"))
            intColor = Color.ALICEBLUE;
        else if (colorStr.equalsIgnoreCase("ANTIQUEWHITE"))
            intColor = Color.ANTIQUEWHITE;
        else if (colorStr.equalsIgnoreCase("AQUA"))
            intColor = Color.AQUA;
        else if (colorStr.equalsIgnoreCase("AQUAMARINE"))
            intColor = Color.AQUAMARINE;
        else if (colorStr.equalsIgnoreCase("AZURE"))
            intColor = Color.AZURE;
        else if (colorStr.equalsIgnoreCase("BEIGE"))
            intColor = Color.BEIGE;
        else if (colorStr.equalsIgnoreCase("BISQUE"))
            intColor = Color.BISQUE;
        else if (colorStr.equalsIgnoreCase("BLACK"))
            intColor = Color.BLACK;
        else if (colorStr.equalsIgnoreCase("BLANCHEDALMOND"))
            intColor = Color.BLANCHEDALMOND;
        else if (colorStr.equalsIgnoreCase("BLUE"))
            intColor = Color.BLUE;
        else if (colorStr.equalsIgnoreCase("BLUEVIOLET"))
            intColor = Color.BLUEVIOLET;
        else if (colorStr.equalsIgnoreCase("BROWN"))
            intColor = Color.BROWN;
        else if (colorStr.equalsIgnoreCase("BURLYWOOD"))
            intColor = Color.BURLYWOOD;
        else if (colorStr.equalsIgnoreCase("CADETBLUE"))
            intColor = Color.CADETBLUE;
        else if (colorStr.equalsIgnoreCase("CHARTREUSE"))
            intColor = Color.CHARTREUSE;
        else if (colorStr.equalsIgnoreCase("CHOCOLATE"))
            intColor = Color.CHOCOLATE;
        else if (colorStr.equalsIgnoreCase("CORAL"))
            intColor = Color.CORAL;
        else if (colorStr.equalsIgnoreCase("CORNFLOWERBLUE"))
            intColor = Color.CORNFLOWERBLUE;
        else if (colorStr.equalsIgnoreCase("CORNSILK"))
            intColor = Color.CORNSILK;
        else if (colorStr.equalsIgnoreCase("CRIMSON"))
            intColor = Color.CRIMSON;
        else if (colorStr.equalsIgnoreCase("CYAN"))
            intColor = Color.CYAN;
        else if (colorStr.equalsIgnoreCase("DARKBLUE"))
            intColor = Color.DARKBLUE;
        else if (colorStr.equalsIgnoreCase("DARKCYAN"))
            intColor = Color.DARKCYAN;
        else if (colorStr.equalsIgnoreCase("DARKGOLDENROD"))
            intColor = Color.DARKGOLDENROD;
        else if (colorStr.equalsIgnoreCase("DARKGRAY"))
            intColor = Color.DARKGRAY;
        else if (colorStr.equalsIgnoreCase("DARKGREY"))
            intColor = Color.DARKGRAY;
        else if (colorStr.equalsIgnoreCase("DARKGREEN"))
            intColor = Color.DARKGREEN;
        else if (colorStr.equalsIgnoreCase("DARKKHAKI"))
            intColor = Color.DARKKHAKI;
        else if (colorStr.equalsIgnoreCase("DARKMAGENTA"))
            intColor = Color.DARKMAGENTA;
        else if (colorStr.equalsIgnoreCase("DARKOLIVEGREEN"))
            intColor = Color.DARKOLIVEGREEN;
        else if (colorStr.equalsIgnoreCase("DARKORANGE"))
            intColor = Color.DARKORANGE;
        else if (colorStr.equalsIgnoreCase("DARKORCHID"))
            intColor = Color.DARKORCHID;
        else if (colorStr.equalsIgnoreCase("DARKRED"))
            intColor = Color.DARKRED;
        else if (colorStr.equalsIgnoreCase("DARKSALMON"))
            intColor = Color.DARKSALMON;
        else if (colorStr.equalsIgnoreCase("DARKSEAGREEN"))
            intColor = Color.DARKSEAGREEN;
        else if (colorStr.equalsIgnoreCase("DARKSLATEBLUE"))
            intColor = Color.DARKSLATEBLUE;
        else if (colorStr.equalsIgnoreCase("DARKSLATEGRAY"))
            intColor = Color.DARKSLATEGRAY;
        else if (colorStr.equalsIgnoreCase("DARKTURQUOISE"))
            intColor = Color.DARKTURQUOISE;
        else if (colorStr.equalsIgnoreCase("DARKVIOLET"))
            intColor = Color.DARKVIOLET;
        else if (colorStr.equalsIgnoreCase("DEEPPINK"))
            intColor = Color.DEEPPINK;
        else if (colorStr.equalsIgnoreCase("DEEPSKYBLUE"))
            intColor = Color.DEEPSKYBLUE;
        else if (colorStr.equalsIgnoreCase("DIMGRAY"))
            intColor = Color.DIMGRAY;
        else if (colorStr.equalsIgnoreCase("DODGERBLUE"))
            intColor = Color.DODGERBLUE;
        else if (colorStr.equalsIgnoreCase("FIREBRICK"))
            intColor = Color.FIREBRICK;
        else if (colorStr.equalsIgnoreCase("FLORALWHITE"))
            intColor = Color.FLORALWHITE;
        else if (colorStr.equalsIgnoreCase("FORESTGREEN"))
            intColor = Color.FORESTGREEN;
        else if (colorStr.equalsIgnoreCase("FUCHSIA"))
            intColor = Color.FUCHSIA;
        else if (colorStr.equalsIgnoreCase("GAINSBORO"))
            intColor = Color.GAINSBORO;
        else if (colorStr.equalsIgnoreCase("GHOSTWHITE"))
            intColor = Color.GHOSTWHITE;
        else if (colorStr.equalsIgnoreCase("GOLD"))
            intColor = Color.GOLD;
        else if (colorStr.equalsIgnoreCase("GOLDENROD"))
            intColor = Color.GOLDENROD;
        else if (colorStr.equalsIgnoreCase("GRAY"))
            intColor = Color.GRAY;
        else if (colorStr.equalsIgnoreCase("GREEN"))
            intColor = Color.GREEN;
        else if (colorStr.equalsIgnoreCase("GREENYELLOW"))
            intColor = Color.GREENYELLOW;
        else if (colorStr.equalsIgnoreCase("HONEYDEW"))
            intColor = Color.HONEYDEW;
        else if (colorStr.equalsIgnoreCase("HOTPINK"))
            intColor = Color.HOTPINK;
        else if (colorStr.equalsIgnoreCase("INDIANRED"))
            intColor = Color.INDIANRED;
        else if (colorStr.equalsIgnoreCase("INDIGO"))
            intColor = Color.INDIGO;
        else if (colorStr.equalsIgnoreCase("IVORY"))
            intColor = Color.IVORY;
        else if (colorStr.equalsIgnoreCase("KHAKI"))
            intColor = Color.KHAKI;
        else if (colorStr.equalsIgnoreCase("LAVENDER"))
            intColor = Color.LAVENDER;
        else if (colorStr.equalsIgnoreCase("LAVENDERBLUSH"))
            intColor = Color.LAVENDERBLUSH;
        else if (colorStr.equalsIgnoreCase("LAWNGREEN"))
            intColor = Color.LAWNGREEN;
        else if (colorStr.equalsIgnoreCase("LEMONCHIFFON"))
            intColor = Color.LEMONCHIFFON;
        else if (colorStr.equalsIgnoreCase("LIGHTBLUE"))
            intColor = Color.LIGHTBLUE;
        else if (colorStr.equalsIgnoreCase("LIGHTCORAL"))
            intColor = Color.LIGHTCORAL;
        else if (colorStr.equalsIgnoreCase("LIGHTCYAN"))
            intColor = Color.LIGHTCYAN;
        else if (colorStr.equalsIgnoreCase("LIGHTGOLDENRODYELLOW"))
            intColor = Color.LIGHTGOLDENRODYELLOW;
        else if (colorStr.equalsIgnoreCase("LIGHTGRAY"))
            intColor = Color.LIGHTGRAY;
        else if (colorStr.equalsIgnoreCase("LIGHTGREEN"))
            intColor = Color.LIGHTGREEN;
        else if (colorStr.equalsIgnoreCase("LIGHTPINK"))
            intColor = Color.LIGHTPINK;
        else if (colorStr.equalsIgnoreCase("LIGHTSALMON"))
            intColor = Color.LIGHTSALMON;
        else if (colorStr.equalsIgnoreCase("LIGHTSEAGREEN"))
            intColor = Color.LIGHTSEAGREEN;
        else if (colorStr.equalsIgnoreCase("LIGHTSKYBLUE"))
            intColor = Color.LIGHTSKYBLUE;
        else if (colorStr.equalsIgnoreCase("LIGHTSLATEGRAY"))
            intColor = Color.LIGHTSLATEGRAY;
        else if (colorStr.equalsIgnoreCase("LIGHTSTEELBLUE"))
            intColor = Color.LIGHTSTEELBLUE;
        else if (colorStr.equalsIgnoreCase("LIGHTYELLOW"))
            intColor = Color.LIGHTYELLOW;
        else if (colorStr.equalsIgnoreCase("LIME"))
            intColor = Color.LIME;
        else if (colorStr.equalsIgnoreCase("LIMEGREEN"))
            intColor = Color.LIMEGREEN;
        else if (colorStr.equalsIgnoreCase("LINEN"))
            intColor = Color.LINEN;
        else if (colorStr.equalsIgnoreCase("MAGENTA"))
            intColor = Color.MAGENTA;
        else if (colorStr.equalsIgnoreCase("MAROON"))
            intColor = Color.MAROON;
        else if (colorStr.equalsIgnoreCase("MEDIUMAQUAMARINE"))
            intColor = Color.MEDIUMAQUAMARINE;
        else if (colorStr.equalsIgnoreCase("MEDIUMBLUE"))
            intColor = Color.MEDIUMBLUE;
        else if (colorStr.equalsIgnoreCase("MEDIUMORCHID"))
            intColor = Color.MEDIUMORCHID;
        else if (colorStr.equalsIgnoreCase("MEDIUMPURPLE"))
            intColor = Color.MEDIUMPURPLE;
        else if (colorStr.equalsIgnoreCase("MEDIUMSEAGREEN"))
            intColor = Color.MEDIUMSEAGREEN;
        else if (colorStr.equalsIgnoreCase("MEDIUMSLATEBLUE"))
            intColor = Color.MEDIUMSLATEBLUE;
        else if (colorStr.equalsIgnoreCase("MEDIUMSPRINGGREEN"))
            intColor = Color.MEDIUMSPRINGGREEN;
        else if (colorStr.equalsIgnoreCase("MEDIUMTURQUOISE"))
            intColor = Color.MEDIUMTURQUOISE;
        else if (colorStr.equalsIgnoreCase("MEDIUMVIOLETRED"))
            intColor = Color.MEDIUMVIOLETRED;
        else if (colorStr.equalsIgnoreCase("MIDNIGHTBLUE"))
            intColor = Color.MIDNIGHTBLUE;
        else if (colorStr.equalsIgnoreCase("MINTCREAM"))
            intColor = Color.MINTCREAM;
        else if (colorStr.equalsIgnoreCase("MISTYROSE"))
            intColor = Color.MISTYROSE;
        else if (colorStr.equalsIgnoreCase("MOCCASIN"))
            intColor = Color.MOCCASIN;
        else if (colorStr.equalsIgnoreCase("NAVAJOWHITE"))
            intColor = Color.NAVAJOWHITE;
        else if (colorStr.equalsIgnoreCase("NAVY"))
            intColor = Color.NAVY;
        else if (colorStr.equalsIgnoreCase("OLDLACE"))
            intColor = Color.OLDLACE;
        else if (colorStr.equalsIgnoreCase("OLIVE"))
            intColor = Color.OLIVE;
        else if (colorStr.equalsIgnoreCase("OLIVEDRAB"))
            intColor = Color.OLIVEDRAB;
        else if (colorStr.equalsIgnoreCase("ORANGE"))
            intColor = Color.ORANGE;
        else if (colorStr.equalsIgnoreCase("ORANGERED"))
            intColor = Color.ORANGERED;
        else if (colorStr.equalsIgnoreCase("ORCHID"))
            intColor = Color.ORCHID;
        else if (colorStr.equalsIgnoreCase("PALEGOLDENROD"))
            intColor = Color.PALEGOLDENROD;
        else if (colorStr.equalsIgnoreCase("PALEGREEN"))
            intColor = Color.PALEGREEN;
        else if (colorStr.equalsIgnoreCase("PALETURQUOISE"))
            intColor = Color.PALETURQUOISE;
        else if (colorStr.equalsIgnoreCase("PALEVIOLETRED"))
            intColor = Color.PALEVIOLETRED;
        else if (colorStr.equalsIgnoreCase("PAPAYAWHIP"))
            intColor = Color.PAPAYAWHIP;
        else if (colorStr.equalsIgnoreCase("PEACHPUFF"))
            intColor = Color.PEACHPUFF;
        else if (colorStr.equalsIgnoreCase("PERU"))
            intColor = Color.PERU;
        else if (colorStr.equalsIgnoreCase("PINK"))
            intColor = Color.PINK;
        else if (colorStr.equalsIgnoreCase("PLUM"))
            intColor = Color.PLUM;
        else if (colorStr.equalsIgnoreCase("POWDERBLUE"))
            intColor = Color.POWDERBLUE;
        else if (colorStr.equalsIgnoreCase("PURPLE"))
            intColor = Color.PURPLE;
        else if (colorStr.equalsIgnoreCase("RED"))
            intColor = Color.RED;
        else if (colorStr.equalsIgnoreCase("ROSYBROWN"))
            intColor = Color.ROSYBROWN;
        else if (colorStr.equalsIgnoreCase("ROYALBLUE"))
            intColor = Color.ROYALBLUE;
        else if (colorStr.equalsIgnoreCase("SADDLEBROWN"))
            intColor = Color.SADDLEBROWN;
        else if (colorStr.equalsIgnoreCase("SALMON"))
            intColor = Color.SALMON;
        else if (colorStr.equalsIgnoreCase("SANDYBROWN"))
            intColor = Color.SANDYBROWN;
        else if (colorStr.equalsIgnoreCase("SEAGREEN"))
            intColor = Color.SEAGREEN;
        else if (colorStr.equalsIgnoreCase("SEASHELL"))
            intColor = Color.SEASHELL;
        else if (colorStr.equalsIgnoreCase("SIENNA"))
            intColor = Color.SIENNA;
        else if (colorStr.equalsIgnoreCase("SILVER"))
            intColor = Color.SILVER;
        else if (colorStr.equalsIgnoreCase("SKYBLUE"))
            intColor = Color.SKYBLUE;
        else if (colorStr.equalsIgnoreCase("SLATEBLUE"))
            intColor = Color.SLATEBLUE;
        else if (colorStr.equalsIgnoreCase("SLATEGRAY"))
            intColor = Color.SLATEGRAY;
        else if (colorStr.equalsIgnoreCase("SNOW"))
            intColor = Color.SNOW;
        else if (colorStr.equalsIgnoreCase("SPRINGGREEN"))
            intColor = Color.SPRINGGREEN;
        else if (colorStr.equalsIgnoreCase("STEELBLUE"))
            intColor = Color.STEELBLUE;
        else if (colorStr.equalsIgnoreCase("TAN"))
            intColor = Color.TAN;
        else if (colorStr.equalsIgnoreCase("TEAL"))
            intColor = Color.TEAL;
        else if (colorStr.equalsIgnoreCase("THISTLE"))
            intColor = Color.THISTLE;
        else if (colorStr.equalsIgnoreCase("TOMATO"))
            intColor = Color.TOMATO;
        else if (colorStr.equalsIgnoreCase("TURQUOISE"))
            intColor = Color.TURQUOISE;
        else if (colorStr.equalsIgnoreCase("VIOLET"))
            intColor = Color.VIOLET;
        else if (colorStr.equalsIgnoreCase("WHEAT"))
            intColor = Color.WHEAT;
        else if (colorStr.equalsIgnoreCase("WHITE"))
            intColor = Color.WHITE;
        else if (colorStr.equalsIgnoreCase("WHITESMOKE"))
            intColor = Color.WHITESMOKE;
        else if (colorStr.equalsIgnoreCase("YELLOW"))
            intColor = Color.YELLOW;
        else if (colorStr.equalsIgnoreCase("YELLOWGREEN"))
            intColor = Color.YELLOWGREEN;
        else {
        	try{
        		intColor = Integer.parseInt(colorStr, 16);
        	}catch(Exception e) {}
        }
        
        return intColor;
    }
    
    public static String getNameOfColor(int intColor) {
        if (intColor == Color.ALICEBLUE)
            return "ALICEBLUE";
        else if (intColor == Color.ANTIQUEWHITE)
            return "ANTIQUEWHITE";
        else if (intColor == Color.AQUA)
            return "AQUA";
        else if (intColor == Color.AQUAMARINE)
            return "AQUAMARINE";
        else if (intColor == Color.AZURE)
            return "AZURE";
        else if (intColor == Color.BEIGE)
            return "BEIGE";
        else if (intColor == Color.BISQUE)
            return "BISQUE";
        else if (intColor == Color.BLACK)
            return "BLACK";
        else if (intColor == Color.BLANCHEDALMOND)
            return "BLANCHEDALMOND";
        else if (intColor == Color.BLUE)
            return "BLUE";
        else if (intColor == Color.BLUEVIOLET)
            return "BLUEVIOLET";
        else if (intColor == Color.BROWN)
            return "BROWN";
        else if (intColor == Color.BURLYWOOD)
            return "BURLYWOOD";
        else if (intColor == Color.CADETBLUE)
            return "CADETBLUE";
        else if (intColor == Color.CHARTREUSE)
            return "CHARTREUSE";
        else if (intColor == Color.CHOCOLATE)
            return "CHOCOLATE";
        else if (intColor == Color.CORAL)
            return "CORAL";
        else if (intColor == Color.CORNFLOWERBLUE)
            return "CORNFLOWERBLUE";
        else if (intColor == Color.CORNSILK)
            return "CORNSILK";
        else if (intColor == Color.CRIMSON)
            return "CRIMSON";
        else if (intColor == Color.CYAN)
            return "CYAN";
        else if (intColor == Color.DARKBLUE)
            return "DARKBLUE";
        else if (intColor == Color.DARKCYAN)
            return "DARKCYAN";
        else if (intColor == Color.DARKGOLDENROD)
            return "DARKGOLDENROD";
        else if (intColor == Color.DARKGRAY)
            return "DARKGRAY";
        else if (intColor == Color.DARKGREEN)
            return "DARKGREEN";
        else if (intColor == Color.DARKKHAKI)
            return "DARKKHAKI";
        else if (intColor == Color.DARKMAGENTA)
            return "DARKMAGENTA";
        else if (intColor == Color.DARKOLIVEGREEN)
            return "DARKOLIVEGREEN";
        else if (intColor == Color.DARKORANGE)
            return "DARKORANGE";
        else if (intColor == Color.DARKORCHID)
            return "DARKORCHID";
        else if (intColor == Color.DARKRED)
            return "DARKRED";
        else if (intColor == Color.DARKSALMON)
            return "DARKSALMON";
        else if (intColor == Color.DARKSEAGREEN)
            return "DARKSEAGREEN";
        else if (intColor == Color.DARKSLATEBLUE)
            return "DARKSLATEBLUE";
        else if (intColor == Color.DARKSLATEGRAY)
            return "DARKSLATEGRAY";
        else if (intColor == Color.DARKTURQUOISE)
            return "DARKTURQUOISE";
        else if (intColor == Color.DARKVIOLET)
            return "DARKVIOLET";
        else if (intColor == Color.DEEPPINK)
            return "DEEPPINK";
        else if (intColor == Color.DEEPSKYBLUE)
            return "DEEPSKYBLUE";
        else if (intColor == Color.DIMGRAY)
            return "DIMGRAY";
        else if (intColor == Color.DODGERBLUE)
            return "DODGERBLUE";
        else if (intColor == Color.FIREBRICK)
            return "FIREBRICK";
        else if (intColor == Color.FLORALWHITE)
            return "FLORALWHITE";
        else if (intColor == Color.FORESTGREEN)
            return "FORESTGREEN";
        else if (intColor == Color.FUCHSIA)
            return "FUCHSIA";
        else if (intColor == Color.GAINSBORO)
            return "GAINSBORO";
        else if (intColor == Color.GHOSTWHITE)
            return "GHOSTWHITE";
        else if (intColor == Color.GOLD)
            return "GOLD";
        else if (intColor == Color.GOLDENROD)
            return "GOLDENROD";
        else if (intColor == Color.GRAY)
            return "GRAY";
        else if (intColor == Color.GREEN)
            return "GREEN";
        else if (intColor == Color.GREENYELLOW)
            return "GREENYELLOW";
        else if (intColor == Color.HONEYDEW)
            return "HONEYDEW";
        else if (intColor == Color.HOTPINK)
            return "HOTPINK";
        else if (intColor == Color.INDIANRED)
            return "INDIANRED";
        else if (intColor == Color.INDIGO)
            return "INDIGO";
        else if (intColor == Color.IVORY)
            return "IVORY";
        else if (intColor == Color.KHAKI)
            return "KHAKI";
        else if (intColor == Color.LAVENDER)
            return "LAVENDER";
        else if (intColor == Color.LAVENDERBLUSH)
            return "LAVENDERBLUSH";
        else if (intColor == Color.LAWNGREEN)
            return "LAWNGREEN";
        else if (intColor == Color.LEMONCHIFFON)
            return "LEMONCHIFFON";
        else if (intColor == Color.LIGHTBLUE)
            return "LIGHTBLUE";
        else if (intColor == Color.LIGHTCORAL)
            return "LIGHTCORAL";
        else if (intColor == Color.LIGHTCYAN)
            return "LIGHTCYAN";
        else if (intColor == Color.LIGHTGOLDENRODYELLOW)
            return "LIGHTGOLDENRODYELLOW";
        else if (intColor == Color.LIGHTGREEN)
            return "LIGHTGREEN";
        else if (intColor == Color.LIGHTPINK)
            return "LIGHTPINK";
        else if (intColor == Color.LIGHTSALMON)
            return "LIGHTSALMON";
        else if (intColor == Color.LIGHTSEAGREEN)
            return "LIGHTSEAGREEN";
        else if (intColor == Color.LIGHTSKYBLUE)
            return "LIGHTSKYBLUE";
        else if (intColor == Color.LIGHTSLATEGRAY)
            return "LIGHTSLATEGRAY";
        else if (intColor == Color.LIGHTSTEELBLUE)
            return "LIGHTSTEELBLUE";
        else if (intColor == Color.LIGHTYELLOW)
            return "LIGHTYELLOW";
        else if (intColor == Color.LIME)
            return "LIME";
        else if (intColor == Color.LIMEGREEN)
            return "LIMEGREEN";
        else if (intColor == Color.LINEN)
            return "LINEN";
        else if (intColor == Color.MAGENTA)
            return "MAGENTA";
        else if (intColor == Color.MAROON)
            return "MAROON";
        else if (intColor == Color.MEDIUMAQUAMARINE)
            return "MEDIUMAQUAMARINE";
        else if (intColor == Color.MEDIUMBLUE)
            return "MEDIUMBLUE";
        else if (intColor == Color.MEDIUMORCHID)
            return "MEDIUMORCHID";
        else if (intColor == Color.MEDIUMPURPLE)
            return "MEDIUMPURPLE";
        else if (intColor == Color.MEDIUMSEAGREEN)
            return "MEDIUMSEAGREEN";
        else if (intColor == Color.MEDIUMSLATEBLUE)
            return "MEDIUMSLATEBLUE";
        else if (intColor == Color.MEDIUMSPRINGGREEN)
            return "MEDIUMSPRINGGREEN";
        else if (intColor == Color.MEDIUMTURQUOISE)
            return "MEDIUMTURQUOISE";
        else if (intColor == Color.MEDIUMVIOLETRED)
            return "MEDIUMVIOLETRED";
        else if (intColor == Color.MIDNIGHTBLUE)
            return "MIDNIGHTBLUE";
        else if (intColor == Color.MINTCREAM)
            return "MINTCREAM";
        else if (intColor == Color.MISTYROSE)
            return "MISTYROSE";
        else if (intColor == Color.MOCCASIN)
            return "MOCCASIN";
        else if (intColor == Color.NAVAJOWHITE)
            return "NAVAJOWHITE";
        else if (intColor == Color.NAVY)
            return "NAVY";
        else if (intColor == Color.OLDLACE)
            return "OLDLACE";
        else if (intColor == Color.OLIVE)
            return "OLIVE";
        else if (intColor == Color.OLIVEDRAB)
            return "OLIVEDRAB";
        else if (intColor == Color.ORANGE)
            return "ORANGE";
        else if (intColor == Color.ORANGERED)
            return "ORANGERED";
        else if (intColor == Color.ORCHID)
            return "ORCHID";
        else if (intColor == Color.PALEGOLDENROD)
            return "PALEGOLDENROD";
        else if (intColor == Color.PALEGREEN)
            return "PALEGREEN";
        else if (intColor == Color.PALETURQUOISE)
            return "PALETURQUOISE";
        else if (intColor == Color.PALEVIOLETRED)
            return "PALEVIOLETRED";
        else if (intColor == Color.PAPAYAWHIP)
            return "PAPAYAWHIP";
        else if (intColor == Color.PEACHPUFF)
            return "PEACHPUFF";
        else if (intColor == Color.PERU)
            return "PERU";
        else if (intColor == Color.PINK)
            return "PINK";
        else if (intColor == Color.PLUM)
            return "PLUM";
        else if (intColor == Color.POWDERBLUE)
            return "POWDERBLUE";
        else if (intColor == Color.PURPLE)
            return "PURPLE";
        else if (intColor == Color.RED)
            return "RED";
        else if (intColor == Color.ROSYBROWN)
            return "ROSYBROWN";
        else if (intColor == Color.ROYALBLUE)
            return "ROYALBLUE";
        else if (intColor == Color.SADDLEBROWN)
            return "SADDLEBROWN";
        else if (intColor == Color.SALMON)
            return "SALMON";
        else if (intColor == Color.SANDYBROWN)
            return "SANDYBROWN";
        else if (intColor == Color.SEAGREEN)
            return "SEAGREEN";
        else if (intColor == Color.SEASHELL)
            return "SEASHELL";
        else if (intColor == Color.SIENNA)
            return "SIENNA";
        else if (intColor == Color.SILVER)
            return "SILVER";
        else if (intColor == Color.SKYBLUE)
            return "SKYBLUE";
        else if (intColor == Color.SLATEBLUE)
            return "SLATEBLUE";
        else if (intColor == Color.SLATEGRAY)
            return "SLATEGRAY";
        else if (intColor == Color.SNOW)
            return "SNOW";
        else if (intColor == Color.SPRINGGREEN)
            return "SPRINGGREEN";
        else if (intColor == Color.STEELBLUE)
            return "STEELBLUE";
        else if (intColor == Color.TAN)
            return "TAN";
        else if (intColor == Color.TEAL)
            return "TEAL";
        else if (intColor == Color.THISTLE)
            return "THISTLE";
        else if (intColor == Color.TOMATO)
            return "TOMATO";
        else if (intColor == Color.TURQUOISE)
            return "TURQUOISE";
        else if (intColor == Color.VIOLET)
            return "VIOLET";
        else if (intColor == Color.WHEAT)
            return "WHEAT";
        else if (intColor == Color.WHITE)
            return "WHITE";
        else if (intColor == Color.WHITESMOKE)
            return "WHITESMOKE";
        else if (intColor == Color.YELLOW)
            return "YELLOW";
        else if (intColor == Color.YELLOWGREEN)
            return "YELLOWGREEN";
        
        return "unknown";
    }

}
