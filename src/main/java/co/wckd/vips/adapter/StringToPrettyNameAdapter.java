package co.wckd.vips.adapter;

import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.vips.entity.section.PrettyName;

public class StringToPrettyNameAdapter implements ObjectAdapter<String, PrettyName> {

    @Override
    public PrettyName adapt(String string) {

        PrettyName prettyName = new PrettyName();

        if (string != null && !string.isEmpty())
            prettyName.setSection(string);

        return prettyName;
    }

}