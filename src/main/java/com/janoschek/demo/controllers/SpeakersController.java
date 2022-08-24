package com.janoschek.demo.controllers;

import com.janoschek.demo.models.Session;
import com.janoschek.demo.models.Speaker;
import com.janoschek.demo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    private List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    private Speaker get(@PathVariable Long id) {
        return speakerRepository.findById(id).orElseThrow();
    }

    @PostMapping
    private Speaker create(@RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        //Becouse this is a put, we expect all of the attributes to be updated. If this was a patch, we would pass
        // only a portion of the attributes in the request body
        //TODO Add validation that all attributes are passed in, otherwise return 400 bad payload
        Speaker existingSpeaker = speakerRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        //TODO Also need to check for children records before deleting.
        speakerRepository.deleteById(id);
    }
}
