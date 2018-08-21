package homework.parser;

import homework.parser.processors.JsoupProcessing;

/**
 * Created by Рома on 20.08.2018.
 */
public enum ProcessorFactory {
    JSOUP_PROCESSOR("jsoup", new JsoupProcessing());

    private final String name;
    private final AbstractProcessor processor;

    ProcessorFactory(String name, AbstractProcessor abstractProcessor) {
        this.name = name;
        this.processor = abstractProcessor;
    }


    public String getName() {
        return name;
    }

    public AbstractProcessor getProcessor() {
        return processor;
    }
}
