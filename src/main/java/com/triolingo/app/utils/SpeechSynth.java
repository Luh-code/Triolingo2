package com.triolingo.app.utils;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;

public class SpeechSynth {
	private Synthesizer voiceSynthesizer;

	public void init()
	{
		try {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
			Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
			voiceSynthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
//			voiceSynthesizer.speakPlainText("Some test text!", null);
			voiceSynthesizer.allocate();
			voiceSynthesizer.resume();
		} catch (EngineException e) {
			throw new RuntimeException(e);
		} catch (AudioException e) {
			throw new RuntimeException(e);
		}
	}

//	public void resume()
//	{
//		try {
//			voiceSynthesizer.allocate();
//			voiceSynthesizer.resume();
//		} catch (EngineException e) {
//			throw new RuntimeException(e);
//		} catch (AudioException e) {
//			throw new RuntimeException(e);
//		}
//	}

	public void end()
	{
		try {
			voiceSynthesizer.deallocate();
		} catch (EngineException e) {
			throw new RuntimeException(e);
		}
	}

	public Synthesizer getVoiceSynthesizer() {
		return voiceSynthesizer;
	}
}
