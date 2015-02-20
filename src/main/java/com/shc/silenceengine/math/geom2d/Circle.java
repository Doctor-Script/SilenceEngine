package com.shc.silenceengine.math.geom2d;

import com.shc.silenceengine.math.Vector2;

/**
 * Represents a Circle Polygon.
 *
 * @author Sri Harsha Chilakapati
 */
public class Circle extends Polygon
{
    private float radius;

    /**
     * Constructs a point-circle, with center as origin and radius as 1
     */
    public Circle()
    {
        this(0, 0, 1);
    }

    /**
     * Constructs a circle with a center position and a radius.
     *
     * @param x      The center x-coordinate
     * @param y      The center y-coordinate
     * @param radius The radius of the circle
     */
    public Circle(float x, float y, float radius)
    {
        this.radius = radius;

        updateVertices();
        setCenter(new Vector2(x, y));
    }

    /**
     * Updates the vertices of the circle. Regenerates them.
     */
    private void updateVertices()
    {
        float numSegments = 10 * radius;

        float theta = (float) (2 * Math.PI / numSegments);
        float c = (float) Math.cos(theta);
        float s = (float) Math.sin(theta);
        float t;

        float x = radius;
        float y = 0;

        clearVertices();
        for (int i = 0; i < numSegments; i++)
        {
            addVertex(new Vector2(x + radius, y + radius));

            t = x;
            x = c * x - s * y;
            y = s * t + c * y;
        }
    }

    /**
     * Checks for intersection between this circle and another polygon.
     *
     * @param p The other Polygon to test for intersection.
     *
     * @return True if intersects, else False.
     */
    public boolean intersects(Polygon p)
    {
        if (p instanceof Circle)
        {
            Circle c = (Circle) p;

            float x = getX();
            float y = getY();

            float cx = c.getX();
            float cy = c.getY();

            return (((x - cx) * (x - cx)) + ((y - cy) * (y - cy))) < (radius + c.radius) * (radius + c.radius);
        }
        else
            return super.intersects(p);
    }

    /**
     * Checks if a point exists inside this circle.
     *
     * @param p The point to check
     *
     * @return True if inside, else False.
     */
    public boolean contains(Vector2 p)
    {
        return (((getX() - p.getX()) * (getX() - p.getX())) + ((getY() - p.getY()) * (getY() - p.getY()))) < radius * radius;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Circle circle = (Circle) o;

        return radius == circle.radius && getX() == circle.getX() && getY() == circle.getY();
    }

    @Override
    public int hashCode()
    {
        int result = (radius != +0.0f ? Float.floatToIntBits(radius) : 0);
        result = 31 * result + (getX() != +0.0f ? Float.floatToIntBits(getX()) : 0);
        result = 31 * result + (getY() != +0.0f ? Float.floatToIntBits(getY()) : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Circle{" +
               "x=" + getX() +
               ", y=" + getY() +
               ", r=" + radius +
               '}';
    }

    public float getX()
    {
        return getCenter().getX();
    }

    public void setX(float x)
    {
        Vector2 center = getCenter();
        center.setX(x);
        setCenter(center);
    }

    public float getY()
    {
        return getCenter().getY();
    }

    public void setY(float y)
    {
        Vector2 center = getCenter();
        center.setY(y);
        setCenter(center);
    }

    public float getRadius()
    {
        return radius;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
        updateVertices();
    }
}
