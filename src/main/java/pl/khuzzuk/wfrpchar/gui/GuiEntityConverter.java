package pl.khuzzuk.wfrpchar.gui;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class GuiEntityConverter<T, U, V> {
    private Supplier<T> itemSupplier;
    private Supplier<U> valueFromController;
    private BiConsumer<T, V> itemSetter;
    @SuppressWarnings("unchecked")
    private Function<U, V> toValueMapper;
    private Predicate<T> filter;
    private static Function defaultMapper = element -> element;
    private static Predicate defaultTest = element -> true;

    @SuppressWarnings("unchecked")
    public GuiEntityConverter(Supplier<T> itemSupplier, Supplier<U> valueFromController, BiConsumer<T, V> itemSetter) {
        this(itemSupplier, valueFromController, itemSetter, defaultMapper);
    }

    @SuppressWarnings("unchecked")
    public GuiEntityConverter(Supplier<T> itemSupplier,
                              Supplier<U> valueFromController,
                              BiConsumer<T, V> itemSetter,
                              Function<U, V> toValueMapper) {
        this(itemSupplier, valueFromController, itemSetter, toValueMapper, defaultTest);
    }

    @SuppressWarnings("unchecked")
    public GuiEntityConverter(Supplier<T> itemSupplier,
                              Supplier<U> valueFromController,
                              BiConsumer<T, V> itemSetter,
                              Predicate<T> filter) {
        this(itemSupplier, valueFromController, itemSetter, defaultMapper, filter);
    }

    public GuiEntityConverter(Supplier<T> itemSupplier,
                              Supplier<U> valueFromController,
                              BiConsumer<T, V> itemSetter,
                              Function<U, V> toValueMapper,
                              Predicate<T> filter) {
        this.itemSupplier = itemSupplier;
        this.valueFromController = valueFromController;
        this.itemSetter = itemSetter;
        this.toValueMapper = toValueMapper;
        this.filter = filter;
    }

    public void fill() {
        itemSetter.accept(itemSupplier.get(), toValueMapper.apply(valueFromController.get()));
    }

    public boolean canConvert() {
        return filter.test(itemSupplier.get());
    }
}
