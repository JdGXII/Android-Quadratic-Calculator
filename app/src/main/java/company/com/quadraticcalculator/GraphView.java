package company.com.quadraticcalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jralz_000 on 4/7/2016.
 */
public class GraphView extends View {

    // Stores graph and line information
    private int gridDimension;
    private int lineSlope;
    private int lineYintercept;

    // Appearance fields
    //These paints are what we use to 'paint' the grid, axis, the equation line
    //the circle with the intercept etc...
    private Paint gridPaint;
    private Paint axisPaint;
    private Paint linePaint;
    private Paint textPaint;
    private Paint circlePaint;


    // Initialize Paints and dimensions
    public void Init()
    {
        // Set initial line slope and y-intercept
        setLineSlope(1);
        setLineYintercept(0);

        // Set initial grid dimension
        setGridDimension(10);

        // Grid line paint
        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(1);
        gridPaint.setColor(Color.GRAY);

        // Axis paint
        axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setStrokeWidth(3);
        axisPaint.setColor(Color.BLACK);

        // Line paint
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);
        linePaint.setColor(Color.BLUE);

        // Text paint
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(1);
        textPaint.setColor(Color.BLACK);

        // Circle paint
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.RED);
    }

    public int getGridDimension() {
        return gridDimension;
    }

    public void setGridDimension(int gridDimension) {
        this.gridDimension = gridDimension;
    }

    public int getLineSlope() {
        return lineSlope;
    }

    public void setLineSlope(int lineSlope) {
        this.lineSlope = lineSlope;
    }

    public int getLineYintercept() {
        return lineYintercept;
    }

    public void setLineYintercept(int lineYintercept) {
        this.lineYintercept = lineYintercept;
    }

    public GraphView(Context context) {
        super(context);
        Init();
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Init();
    }


    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Background color
        canvas.drawColor(Color.WHITE);

        // Draw grid lines in x dimension (vertical lines)
        for (int x = -this.getGridDimension(); x <= this.getGridDimension(); ++x)
            canvas.drawLine(interpX(x), interpY(this.getGridDimension()),
                    interpX(x), interpY(-this.getGridDimension()),
                    (x == 0) ? axisPaint : gridPaint);

        // Draw grid lines in y dimension (horizontal lines)
        for (int y = -this.getGridDimension(); y <= this.getGridDimension(); ++y)
            canvas.drawLine(interpX(-this.getGridDimension()), interpY(y),
                    interpX(this.getGridDimension()), interpY(y),
                    (y == 0) ? axisPaint : gridPaint);

        // Draw coordinate text
        int step = this.getGridDimension() / 4;
        textPaint.setTextAlign(Paint.Align.CENTER);
        for (int x = -this.getGridDimension() + step; x < this
                .getGridDimension(); x += step) {
            if (x != 0)
                canvas.drawText(Integer.toString(x), interpX(x), interpY(0),
                        textPaint);
        }
        textPaint.setTextAlign(Paint.Align.LEFT);
        for (int y = -this.getGridDimension() + step; y < this
                .getGridDimension(); y += step)
            canvas.drawText(Integer.toString(y), interpX(0), interpY(y),
                    textPaint);

        // Compute start and end line coordinates
        double x0 = -this.getGridDimension();
        double y0 = solveLineEq(x0);
        double x1 = this.getGridDimension();
        double y1 = solveLineEq(x1);

        // Draw line
        canvas.drawLine(interpX(x0), interpY(y0), interpX(x1), interpY(y1),
                linePaint);

        // Draw y-intercept point
        double xp = 0.0;
        double yp = solveLineEq(xp);
        canvas.drawCircle(interpX(xp), interpY(yp), this.getWidth()
                * (float) 0.02, circlePaint);
    }

    // Interpolate from graph to Canvas coordinates
    private float interpX(double x) {
        double width = (double) this.getWidth();
        return (float) ((x + this.getGridDimension())
                / (this.getGridDimension() * 2) * width);
    }

    private float interpY(double y) {
        double height = (double) this.getHeight();
        return (float) ((y + this.getGridDimension())
                / (this.getGridDimension() * 2) * -height + height);
    }

    // Line equation result
    private double solveLineEq(double x) {
        return (double) lineSlope * x + (double) lineYintercept;
    }
    

    /**
     * <p>
     * Measure the view and its content to determine the measured width and the
     * measured height. This method is invoked by {@link #measure(int, int)} and
     * should be overridden by subclasses to provide accurate and efficient
     * measurement of their contents.
     * </p>
     * <p/>
     * <p>
     * <strong>CONTRACT:</strong> When overriding this method, you
     * <em>must</em> call {@link #setMeasuredDimension(int, int)} to store the
     * measured width and height of this view. Failure to do so will trigger an
     * <code>IllegalStateException</code>, thrown by
     * {@link #measure(int, int)}. Calling the superclass'
     * {@link #onMeasure(int, int)} is a valid use.
     * </p>
     * <p/>
     * <p>
     * The base class implementation of measure defaults to the background size,
     * unless a larger size is allowed by the MeasureSpec. Subclasses should
     * override {@link #onMeasure(int, int)} to provide better measurements of
     * their content.
     * </p>
     * <p/>
     * <p>
     * If this method is overridden, it is the subclass's responsibility to make
     * sure the measured height and width are at least the view's minimum height
     * and width ({@link #getSuggestedMinimumHeight()} and
     * {@link #getSuggestedMinimumWidth()}).
     * </p>
     *
     * @param widthMeasureSpec  horizontal space requirements as imposed by the parent.
     *                          The requirements are encoded with
     *                          {@link MeasureSpec}.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     *                          The requirements are encoded with
     *                          {@link MeasureSpec}.
     * @see #getMeasuredWidth()
     * @see #getMeasuredHeight()
     * @see #setMeasuredDimension(int, int)
     * @see #getSuggestedMinimumHeight()
     * @see #getSuggestedMinimumWidth()
     * @see MeasureSpec#getMode(int)
     * @see MeasureSpec#getSize(int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    //Discounts the left and right padding to the width
    private int measureWidth(int widthMeasureSpec)
    {
        int specSize = MeasureSpec.getSize(widthMeasureSpec) -
                this.getPaddingLeft() - this.getPaddingRight();
        return specSize;
    }
    private int measureHeight(int heightMeasureSpec)
    {
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        return specSize;
    }
}
