package com.shc.silenceengine.geom3d;

import com.shc.silenceengine.collision.Collision3D;
import com.shc.silenceengine.math.Quaternion;
import com.shc.silenceengine.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sri Harsha Chilakapati
 */
public class Polyhedron
{
    private Vector3 position;

    private List<Vector3> vertices;

    private float minX;
    private float minY;
    private float minZ;
    private float maxX;
    private float maxY;
    private float maxZ;

    private float rotationX;
    private float rotationY;
    private float rotationZ;

    private Cuboid bounds;

    private Quaternion tempQuat;

    public Polyhedron()
    {
        vertices = new ArrayList<>();
        position = new Vector3();

        tempQuat = new Quaternion();

        clearVertices();
    }

    public Polyhedron(Polyhedron other)
    {
        this();

        position.set(other.position);

        other.vertices.forEach(vertices::add);
    }

    protected void clearVertices()
    {
        vertices.clear();

        minX = minY = minZ = Float.POSITIVE_INFINITY;
        maxX = maxY = maxZ = Float.NEGATIVE_INFINITY;
    }

    protected void addVertex(Vector3 v)
    {
        vertices.add(v);

        minX = Math.min(minX, v.x);
        minY = Math.min(minY, v.y);
        minZ = Math.min(minZ, v.z);
        maxX = Math.max(maxX, v.x);
        maxY = Math.max(maxY, v.y);
        maxZ = Math.max(maxZ, v.z);
    }

    public void rotate(float rx, float ry, float rz)
    {
        minX = minY = minZ = Float.POSITIVE_INFINITY;
        maxX = maxY = maxZ = Float.NEGATIVE_INFINITY;

        for (Vector3 v : vertices)
        {
            tempQuat.set(rx, ry, rz).multiply(v, v);

            minX = Math.min(minX, v.x);
            minY = Math.min(minY, v.y);
            minZ = Math.min(minZ, v.z);
            maxX = Math.max(maxX, v.x);
            maxY = Math.max(maxY, v.y);
            maxZ = Math.max(maxZ, v.z);
        }

        if (bounds == null)
            bounds = new Cuboid(new Vector3(minX, minY, minZ).add(position),
                    new Vector3(maxX, maxY, maxZ).add(position));
        else
            bounds.set(maxX - minX, maxY - minY, maxZ - minZ, position);

        rotationX += rx;
        rotationY += ry;
        rotationZ += rz;
    }

    public void scale(float s)
    {
        scale(s, s, s);
    }

    public void scale(float sx, float sy, float sz)
    {
        minX = minY = minZ = Float.POSITIVE_INFINITY;
        maxX = maxY = maxZ = Float.NEGATIVE_INFINITY;

        for (Vector3 v : vertices)
        {
            v.scaleSelf(sx, sy, sz);

            minX = Math.min(minX, v.x);
            minY = Math.min(minY, v.y);
            minZ = Math.min(minZ, v.z);
            maxX = Math.max(maxX, v.x);
            maxY = Math.max(maxY, v.y);
            maxZ = Math.max(maxZ, v.z);
        }

        if (bounds == null)
            bounds = new Cuboid(new Vector3(minX, minY, minZ).add(position),
                    new Vector3(maxX, maxY, maxZ).add(position));
        else
            bounds.set(maxX - minX, maxY - minY, maxZ - minZ, position);
    }

    public void translate(float x, float y, float z)
    {
        minX = minY = minZ = Float.POSITIVE_INFINITY;
        maxX = maxY = maxZ = Float.NEGATIVE_INFINITY;

        for (Vector3 v : vertices)
        {
            v.addSelf(x, y, z);

            minX = Math.min(minX, v.x);
            minY = Math.min(minY, v.y);
            minZ = Math.min(minZ, v.z);
            maxX = Math.max(maxX, v.x);
            maxY = Math.max(maxY, v.y);
            maxZ = Math.max(maxZ, v.z);
        }

        if (bounds == null)
            bounds = new Cuboid(new Vector3(minX, minY, minZ).add(position),
                    new Vector3(maxX, maxY, maxZ).add(position));
        else
            bounds.set(maxX - minX, maxY - minY, maxZ - minZ, position);
    }

    public boolean intersects(Polyhedron other)
    {
        return Collision3D.testPolyhedronCollision(this, other);
    }

    public Polyhedron copy()
    {
        return new Polyhedron(this);
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public void setPosition(Vector3 position)
    {
        this.position.set(position);

        if (bounds != null)
            bounds.setPosition(position);
    }

    public int vertexCount()
    {
        return vertices.size();
    }

    public List<Vector3> getVertices()
    {
        return vertices;
    }

    public Vector3 getVertex(int index)
    {
        return vertices.get(index);
    }

    public Cuboid getBounds()
    {
        if (bounds == null)
            bounds = new Cuboid(new Vector3(minX, minY, minZ).add(position),
                    new Vector3(maxX, maxY, maxZ).add(position));

        return bounds;
    }

    public float getWidth()
    {
        return maxX - minX;
    }

    public float getHeight()
    {
        return maxY - minY;
    }

    public float getThickness()
    {
        return maxZ - minZ;
    }

    public float getRotationX()
    {
        return rotationX;
    }

    public float getRotationY()
    {
        return rotationY;
    }

    public float getRotationZ()
    {
        return rotationZ;
    }
}