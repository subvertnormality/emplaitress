Emplaitress {
    *initClass {
        StartUp.add {
            "ALL HAIL THE EMPLAITRESS".postln;
            SynthDef(\plaitsPerc, {
                |out, pitch=60.0, engine=0, harm=0.1, timbre=0.5, morph=0.5, fm_mod=0.0, timb_mod=0.0,
	    	        morph_mod=0.0, decay=0.5, lpg_color=0.5, mul=1.0, aux_mix=0.0, gain=1.0, pan=0.0|
	    	    var sound = MiPlaits.ar(
	    	            pitch: pitch, 
	    	            engine: engine, 
	    	            harm: harm, 
	    	            timbre: timbre, 
	    	            morph: morph,
	    	            trigger: Impulse.kr(0),
	    	            fm_mod: fm_mod,
	    	            timb_mod: timb_mod, 
	    	            morph_mod: morph_mod, 
	    	            decay: decay, 
	    	            lpg_colour: lpg_color, 
	    	            mul: mul);
	    	    sound = SelectX.ar(aux_mix, sound);
	    	    sound = LeakDC.ar(sound);
	    	    DetectSilence.ar(sound, doneAction: Done.freeSelf);
	    	    sound = (gain*sound).softclip;
	    	    Out.ar(out, Pan2.ar(sound, pan));
	        }).add;

	    	OSCFunc.new({ |msg, time, addr, recvPort|
	    	    var args = [[\pitch, \engine, \harm, \timbre, \morph, \fm_mod, \timb_mod, \morph_mod, \decay, \lpg_color, \mul, \aux_mix, \gain, \pan], msg[1..]].lace;
	    	    Synth.new(\plaitsPerc, args);
	    	}, "/emplaitress/perc");
        }
    }
}