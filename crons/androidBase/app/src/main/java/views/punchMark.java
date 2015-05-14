package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class punchMark extends View {
	Paint paint = new Paint();
	private int punchNum;
	public punchMark(Context context, int i){
		super(context);
		punchNum = i;
	}
	
	@Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        paint.setAlpha(200);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        double vertOffset= (height*.35);
        double horizOffset = ((width*.05)); 
        //drawRect(topX, topY, bottomX, bottomY)
        Log.d("itp341", "hello from onDraw");
        double rectWidth = (width*.1);
        double rectHeight = (width*.1);
        
        double xoffset = ((width*.1));
        if(punchNum <4){
        	paint.setColor(Color.RED);
        	paint.setStrokeWidth(10);
        	canvas.drawRect((int)(horizOffset+xoffset*(2*punchNum)), (int)(vertOffset+xoffset), (int)(horizOffset+xoffset*(2*punchNum+1)), (int)(vertOffset+xoffset*2), paint );
        }else if (punchNum>4){
        	paint.setColor(Color.RED);
        	paint.setStrokeWidth(10);
        	canvas.drawRect((int)(horizOffset+xoffset*(2*punchNum)), (int)(vertOffset+xoffset*3), (int)(horizOffset+xoffset*(2*punchNum+1)), (int)(vertOffset+xoffset*4), paint );
        }

    }

}
