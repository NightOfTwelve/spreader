package com.nali.spreader.test.assertion;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;


/**
 * 
 * @author gavin
 *
 */
public class ExceptionAssert extends Assert{
    public static void assertThrowExceptionExactly(Class<? extends Throwable> clazz, ExceptionClosure callback) {
        try {
            callback.execute();
            fail("Haven't throw any exception!");
        } catch (AssertionFailedError assertError) {
            assertError.printStackTrace();
            fail(assertError.getMessage());
        } catch (Throwable other) {
            other.printStackTrace();
            assertEquals(clazz.getName(), other.getClass().getName());
        }
    }

   public static void assertNotThrowExceptionExactly(Class<? extends Throwable> clazz, ExceptionClosure callback) {
        try {
            callback.execute();
        } catch (AssertionFailedError assertError) {
            assertError.printStackTrace();
            fail(assertError.getMessage());
        } catch (Throwable other) {
            other.printStackTrace();
            assertFalse("Have throw the " + clazz + " exception!", clazz.getName().equals(
                    other.getClass().getName()));
        }
    }

   public static void assertThrowException(Class<? extends Throwable> clazz, ExceptionClosure callback) {
        try {
            callback.execute();
            fail("Haven't throw any exception!");
        } catch (AssertionFailedError assertError) {
            assertError.printStackTrace();
            fail(assertError.getMessage());
        } catch (Throwable other) {
            other.printStackTrace();
            assertTrue(other + " is not a type of " + clazz, clazz.isAssignableFrom(other.getClass()));
        }
    }

   public static void assertNotThrowException(Class<? extends Throwable> clazz, ExceptionClosure callback) {
        try {
            callback.execute();
        } catch (AssertionFailedError assertError) {
            assertError.printStackTrace();
            fail(assertError.getMessage());
        } catch (Throwable other) {
            other.printStackTrace();
            assertFalse(other + " is a type of " + clazz, clazz.isAssignableFrom(other.getClass()));
        }
    }

   public static void assertNotThrowAnyException(ExceptionClosure callback) {
        try {
            callback.execute();
        } catch (AssertionFailedError assertError) {
            assertError.printStackTrace();
            fail(assertError.getMessage());
        } catch (Throwable other) {
           other.printStackTrace();
           fail("Throw exception: " + other);
        }
    }
}
