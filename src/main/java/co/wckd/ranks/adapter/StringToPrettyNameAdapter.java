package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.boilerplate.util.Strings;
import co.wckd.ranks.entity.section.PrettyName;

public class StringToPrettyNameAdapter implements ObjectAdapter<String, PrettyName> {

    @Override
    public PrettyName adapt(String string) {

        PrettyName prettyName = new PrettyName();

        if (string != null && !string.isEmpty())
            prettyName.setSection(Strings.colorize(string));

        return prettyName;
    }

}