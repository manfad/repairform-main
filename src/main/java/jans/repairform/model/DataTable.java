package jans.repairform.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataTable<T> {
	
    static int draw = 1;
	long recordsFiltered;
	long recordsTotal;
	List<T> data;
}

