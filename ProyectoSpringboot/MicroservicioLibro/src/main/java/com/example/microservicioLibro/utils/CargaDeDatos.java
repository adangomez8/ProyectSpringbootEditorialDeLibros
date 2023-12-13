package com.example.microservicioLibro.utils;



import org.springframework.stereotype.Component;

@Component
public class CargaDeDatos {
//    @Autowired
//    private final MonopatinRepository monopatinRepository;
//    @Autowired
//    private final ParadaRepository paradaRepository;
//    public CargaDeDatos(MonopatinRepository n, ParadaRepository pr ) {
//        this.monopatinRepository = n;
//        paradaRepository = pr;
//    }
//    @PostConstruct
//    public void readCsvFile() {
//        try {
//            FileReader fileReader2 = new FileReader("C:\\Users\\joseg\\IdeaProjects\\Arquitecturas\\DAOProyect\\Microservicios\\microservicio-monopatin\\src\\main\\resources\\paradas.csv");
//            CSVParser csvParser2 = new CSVParser(fileReader2, CSVFormat.DEFAULT.withHeader());
//            for (CSVRecord record : csvParser2) {
//                System.out.println(record);
//                String latitud = record.get("latitud");
//                String longitud = record.get("longitud");
//                Parada p = new Parada(latitud,longitud);
//                paradaRepository.save(p);
//            }
//
//            FileReader fileReader = new FileReader("C:\\Users\\joseg\\IdeaProjects\\Arquitecturas\\DAOProyect\\Microservicios\\microservicio-monopatin\\src\\main\\resources\\monopatines.csv");
//            List<Parada> listP = paradaRepository.findAll();
//            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader());
//            for (CSVRecord record : csvParser) {
//                Parada paradaAleatoria = listP.get(new Random().nextInt(listP.size()));
                // Accede a las columnas por nombre o índice
//                String id = record.get("id");
//                String estado = record.get("estado");
//                String kilometros = record.get("kilometros");
//                String lat = record.get("latitud");
//                String lon = record.get("longitud");
//                String tiempoUso = record.get("tiempoEnUso");
//                String tiempoPausa = record.get("tiempoEnPausa");
//                Parada p = paradaRepository.findById(Integer.parseInt(record.get("idParada"))).get();
//                Monopatin m = new Monopatin(Integer.parseInt(id),estado,lat,lon,paradaAleatoria);
//                m.setParada(p);
//                m.setKilometros(Integer.parseInt(kilometros));
//                m.setTiempoEnPausa(Integer.parseInt(tiempoPausa));
//                m.setTiempoEnUso(Integer.parseInt(tiempoUso));
//                monopatinRepository.save(m);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}