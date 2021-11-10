package util;


public class StringValidator<T> extends Validator<T,String>{
    protected StringValidator(ToStringFunction<T> keyExtractor) {
        super(keyExtractor::applyAsString);
    }

    protected StringValidator(ToStringFunction<T> keyExtractor, IValidator<T> validator) {
        super(keyExtractor::applyAsString, validator);
    }

    public StringValidator<T> startsWith(String prefix){
        super.thenVaildating(n -> n.startsWith(prefix));
        return this;
    }

    public StringValidator<T> startsWith(String prefix, int toffset){
        super.thenVaildating(n -> n.startsWith(prefix,toffset));
        return this;
    }

    public StringValidator<T> endsWith(String suffix){
        super.thenVaildating(n -> n.endsWith(suffix));
        return this;
    }

    public StringValidator<T> isEmpty(){
        super.thenVaildating(String::isEmpty);
        return this;
    }

    public StringValidator<T> isNotEmpty(){
        super.thenVaildating(n -> !n.isEmpty());
        return this;
    }

    public StringValidator<T> isBlank(){
        super.thenVaildating(String::isBlank);
        return this;
    }

    public StringValidator<T> isNotBlank(){
        super.thenVaildating(n -> !n.isBlank());
        return this;
    }

    public StringValidator<T> length(int length){
        super.thenVaildating(n -> n.length() == length);
        return this;
    }

    public StringValidator<T> matchesRegex(String regex){
        super.thenVaildating(n -> n.matches(regex));
        return this;
    }

    public StringValidator<T> contains(CharSequence charSequence){
        super.thenVaildating(n -> n.contains(charSequence));
        return this;
    }

    public StringValidator<T> equals(String string){
        super.thenVaildating(n -> n.equals(string));
        return this;
    }





}
