package be.alexandre01.inazuma.uhc.utils; /**
 * Project: PrincepsLib
 * Created by Alex D. (SpatiumPrinceps)
 * Date: 12/4/17
 */
import com.google.common.base.Preconditions;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.lang.reflect.Field;
import java.util.List;

public class CustomComponentBuilder extends net.md_5.bungee.api.chat.ComponentBuilder {


    public CustomComponentBuilder(net.md_5.bungee.api.chat.ComponentBuilder original) {
        super(original);
    }

    public CustomComponentBuilder(String text) {
        super(text);
    }

    public net.md_5.bungee.api.chat.ComponentBuilder append(BaseComponent... components) {
        return this.append(components, net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention.ALL);
    }



    public net.md_5.bungee.api.chat.ComponentBuilder append(BaseComponent[] components, net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention retention) {
        Preconditions.checkArgument(components.length != 0, "No components to append");
        BaseComponent[] var3 = components;
        int var4 = components.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            BaseComponent component = var3[var5];

            try {
                Field current = findFieldInSuperClasses(this.getClass(), "current");
                current.setAccessible(true);

                Field parts = findFieldInSuperClasses(this.getClass(), "parts");
                parts.setAccessible(true);
                List partsList = (List) parts.get(this);
                partsList.add(current.get(this));

                current.set(this, component.duplicate());

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            this.retain(retention);
        }

        return this;
    }

    private Field findFieldInSuperClasses(Class<?> classy, String name) {
        Class<?> current = classy;
        do {
            try {
                return current.getDeclaredField(name);
            } catch (Exception ignored) {
            }
        } while ((current = current.getSuperclass()) != null);
        return null;
    }
}