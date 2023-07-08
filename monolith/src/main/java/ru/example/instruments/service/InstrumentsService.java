package ru.example.instruments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.example.instruments.model.Exchange;
import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;
import ru.example.instruments.model.bond.Bond;
import ru.example.instruments.model.bond.BondStatic;
import ru.example.instruments.model.equity.Equity;
import ru.example.instruments.model.equity.EquityStatic;
import ru.example.instruments.out.repository.InstrumentRepository;
import ru.example.instruments.util.InstrumentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InstrumentsService {

    private final InstrumentRepository instrumentRepository;

    public List<Instrument> getInstruments(List<String> ids) {
        List<Instrument> foundInstruments = instrumentRepository.findAllByFigiIn(ids);
        if (CollectionUtils.isEmpty(foundInstruments)) {
            foundInstruments = instrumentRepository.findByIsins(ids);
        }
        return foundInstruments;
    }

    public List<String> getIsinsByExchange(Exchange exchange) {
        List<Instrument> instruments = instrumentRepository.findAllByExchange(exchange);

        List<Bond> bonds = InstrumentUtils.getBonds(instruments);
        List<Equity> equities = InstrumentUtils.getEquities(instruments);

        Set<String> isins = bonds.stream()
                .map(Bond::getStaticData)
                .filter(Objects::nonNull)
                .map(BondStatic::getIsin)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        Set<String> equityIsins = equities.stream()
                .map(Equity::getStaticData)
                .filter(Objects::nonNull)
                .map(EquityStatic::getIsin)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        isins.addAll(equityIsins);
        return new ArrayList<>(isins);
    }

    public Set<String> getBondIsins() {
        List<Instrument> bonds = instrumentRepository.findByType(InstrumentType.BOND);
        return bonds.stream()
                .map(Bond.class::cast)
                .map(Bond::getStaticData)
                .filter(Objects::nonNull)
                .map(BondStatic::getIsin)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
    }

    public List<Instrument> findInstruments(List<String> instrumentIds) {
        return instrumentRepository.findAllByFigiIn(instrumentIds);
    }

    public List<Instrument> searchInstrument(String isin, final InstrumentType instrumentType) {
        Instrument instrument = Instrument.builder().ticker(isin).type(instrumentType).build();
        return find(instrument, PageRequest.of(0, 5));
    }

    public List<Instrument> findByIsin(String isin) {
        return instrumentRepository.findByIsin(isin);
    }

    public List<Instrument> find(Instrument instrument, PageRequest pageRequest) {
        Example<Instrument> example = Example.of(instrument);
        return instrumentRepository.findAll(example, pageRequest).getContent();
    }

    public List<Instrument> get(int page, int size, List<InstrumentType> types) {
        return instrumentRepository.findAllByTypeIn(types, PageRequest.of(page, size));
    }

    public long getInstrumentsCount() {
        return instrumentRepository.count();
    }

    public List<Instrument> findByIsins(List<String> isins) {
        return instrumentRepository.findByIsins(isins);
    }

    public void save(Instrument instrument) {
        instrumentRepository.save(instrument);
    }

    public long getCount(InstrumentType type) {
        return instrumentRepository.countByType(type);
    }
}
