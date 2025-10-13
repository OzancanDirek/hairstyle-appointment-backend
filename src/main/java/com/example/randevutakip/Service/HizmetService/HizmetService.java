package com.example.randevutakip.Service.HizmetService;

import com.example.randevutakip.Repository.HizmetRepository;
import com.example.randevutakip.model.Hizmet;

import java.util.List;

public class HizmetService implements HizmetServiceImpl
{
    private HizmetRepository _hizmetRepository;

    public HizmetService(HizmetRepository hizmetRepository)
    {
        this._hizmetRepository = hizmetRepository;
    }

    @Override
    public List<Hizmet> listAllHizmet()
    {
        return _hizmetRepository.findAll();
    }
}
