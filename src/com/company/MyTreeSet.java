package com.company;

import java.util.Stack;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class MyTreeSet<E> implements Iterable<E>
{
    private TreeNode<E> root;



    public MyTreeSet()
    {
        root = null;
    }



    public boolean contains( E value )
    {
        return contains( root, value );
    }



    public boolean add( E value )
    {
        if ( contains( value ) )
            return false;
        root = add( root, value );
        return true;
    }



    public boolean remove( E value )
    {
        if ( !contains( value ) )
            return false;
        root = remove( root, value );
        return true;
    }



    public String toString()
    {
        String str = toString( root );
        if ( str.endsWith( ", " ) )
            str = str.substring( 0, str.length() - 2 );
        return "[" + str + "]";
    }



    public Iterator<E> iterator()
    {
        return new MyTreeSetIterator( root );
    }


    private boolean contains( TreeNode<E> node, E value )
    {
        if ( node == null )
            return false;
        else
        {
            int diff = ( (Comparable<E>)value ).compareTo( node.getValue() );
            if ( diff == 0 )
                return true;
            else if ( diff < 0 )
                return contains( node.getLeft(), value );
            else
                // if (diff > 0)
                return contains( node.getRight(), value );
        }
    }



    private TreeNode<E> add( TreeNode<E> node, E value )
    {
        if ( node == null )
            node = new TreeNode( value );
        else
        {
            int diff = ( (Comparable<E>)value ).compareTo( node.getValue() );
            if ( diff < 0 )
                node.setLeft( add( node.getLeft(), value ) );
            else
                // if (diff > 0)
                node.setRight( add( node.getRight(), value ) );
        }
        return node;
    }



    private TreeNode<E> remove( TreeNode<E> node, E value )
    {
        int diff = ( (Comparable<E>)value ).compareTo( node.getValue() );
        if ( diff == 0 ) // base case
            node = removeRoot( node );
        else if ( diff < 0 )
            node.setLeft( remove( node.getLeft(), value ) );
        else
            // if (diff > 0)
            node.setRight( remove( node.getRight(), value ) );
        return node;
    }



    private TreeNode<E> removeRoot( TreeNode<E> root )
    {
        TreeNode<E> node = root.getRight();
        TreeNode<E> prev = root;
        if ( node == null && root.getLeft() != null )
        {
            prev = root.getLeft();
            root.setLeft( null );
            return prev;
        }
        else if (node == null && root.getLeft()==null)
        {
            return null;
        }

        while ( node.getLeft() != null )
        {
            prev = node;
            node = node.getLeft();
        }

        if ( prev != root )
            prev.setLeft( null );
        node.setLeft( root.getLeft() );

        if ( root.getRight() == node )
        {
            root.setRight( null );
        }
        else
            node.setRight( root.getRight() );

        return node;
    }



    public void printSideways()
    {
        if ( root == null )
            return;
        printSideways( root, 0 );
    }



    private void printSideways( TreeNode<E> t, int depth )
    {
        if ( t.getRight() != null )
            printSideways( t.getRight(), depth + 1 );

        process( t.getValue(), depth );

        if ( t.getLeft() != null )
            printSideways( t.getLeft(), depth + 1 );
    }



    private void process( E obj, int depth )
    {
        for ( int j = 1; j <= depth; j++ )
            System.out.print( "    " );
        System.out.println( obj.toString() );
    }



    private String toString( TreeNode<E> node )
    {
        if ( node == null )
            return "";
        else
            return toString( node.getLeft() ) + node.getValue() + ", "
                    + toString( node.getRight() );
    }



    private class MyTreeSetIterator implements Iterator<E>
    {
        Stack<TreeNode<E>> stack;

        TreeNode<E> tNode;


        public MyTreeSetIterator( TreeNode<E> root )
        {
            stack = new Stack();
            tNode = root;
            while ( tNode != null )
            {
                stack.push( tNode );
                tNode = tNode.getLeft();
            }
        }

        public boolean hasNext()
        {
            return !stack.isEmpty();
        }

        public E next()
        {
            if ( stack.isEmpty() )
            {
                throw new NoSuchElementException();
            }

            tNode = stack.pop();
            Object obj = tNode.getValue();
            tNode = tNode.getRight();

            while ( tNode != null )
            {
                stack.push( tNode );
                tNode = tNode.getLeft();
            }

            return (E)obj;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

}
