package com.rittmann.robbie.support;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

final class WithIdMatcherConcat extends TypeSafeMatcher<View> {

    private final int id;
    private final int idConcat;

    @RemoteMsgConstructor
    private WithIdMatcherConcat(final int id, final int idConcat) {
        this.id = id;
        this.idConcat = idConcat;
    }

    @Override
    public void describeTo(Description description) {
        String idDescription = id + " " + idConcat;
        description.appendText("with id: " + idDescription);
    }

    @Override
    public boolean matchesSafely(View view) {
        if (view.getId() == idConcat && view.getParent() instanceof LinearLayout)
            return ((ViewGroup) view.getParent()).getId() == id;
        return false;
    }

    public static Matcher<View> withIdConcatened(final int id, final int idConcat) {
        return withId(id, idConcat);
    }

    public static Matcher<View> withId(final int id, final int idConcat) {
        return new WithIdMatcherConcat(id, idConcat);
    }
}